package com.sumscope.cdh.webbond;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Sets;
import com.sumscope.cdh.sumscopemq4j.Sender;
import com.sumscope.cdh.webbond.generated.WebbondBbo;
import com.sumscope.cdh.webbond.generated.WebbondTrade;
import com.sumscope.cdh.webbond.model.BondSearchRequest;
import com.sumscope.cdh.webbond.model.GetDictionaryRequest;
import com.sumscope.cdh.webbond.model.LogonRequest;
import com.sumscope.cdh.webbond.model.LogonResponse;
import com.sumscope.cdh.webbond.model.RedisFilter;
import com.sumscope.cdh.webbond.model.RedisLogonInfo;
import com.sumscope.cdh.webbond.model.RequestBase;
import com.sumscope.cdh.webbond.model.ResponseBase;
import com.sumscope.cdh.webbond.model.UserAccount;
import com.sumscope.cdh.webbond.rabbitmq.RabbitMqMessage;
import com.sumscope.cdh.webbond.request.BondFilter;
import com.sumscope.cdh.webbond.request.ComplexFilter;
import com.sumscope.cdh.webbond.request.FilterByBondRequest;
import com.sumscope.cdh.webbond.utils.BboFilterTool;
import com.sumscope.cdh.webbond.utils.CdcFilterTool;
import com.sumscope.cdh.webbond.utils.JsonTool;
import com.sumscope.cdh.webbond.utils.TradeFilterTool;

@Singleton
@Component
@Path("/webbond")
public class Main
{

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private ExecutorService executorService;

    private Sender notificationSender;

    private final TradeFilterTool tradeFilterTool = new TradeFilterTool();
    private final BboFilterTool bboFilterTool = new BboFilterTool();
    private final CdcFilterTool cdcFilterTool = new CdcFilterTool();

    private final ObjectMapper mapper = JsonTool.createObjectMapper();

    @Autowired
    private Repository repo;

    @Autowired
    private Config config;

    @Autowired
    private MqManager mqManager;

    @Autowired
    public RedisCacheV2 redis;

    @PostConstruct
    private void init0() throws Exception
    {
        notificationSender = mqManager.getSender(config.getRabbitmqOutputExchangeName(), config.getRabbitmqOutputExchangeType());
        executorService = Executors.newFixedThreadPool(this.config.getThreadPoolSize());
    }

    public RedisLogonInfo checkLogon(RequestBase request) throws Exception
    {
        if (null == request.getToken())
        {
            throw WebBondException.PredefinedExceptions.EmptyToken;
        }
        else
        {
            String logonInfo = redis.hgetex(config.getRedisLogon(), request.getToken());
            if (null == logonInfo || "".equals(logonInfo))
            {
                throw WebBondException.PredefinedExceptions.NoLogon;
            }
            return mapper.readValue(logonInfo, RedisLogonInfo.class);
        }
    }

    private void doRequest(org.glassfish.grizzly.http.server.Request contextRequest, AsyncResponse asyncResponse, RequestBase bodyRequest, Callable callable)
    {
        executorService.submit(() ->
        {
            long t0 = System.currentTimeMillis();
            String requestInfo = String.format("%s(%s:%d)", contextRequest.getRequestURI(), contextRequest.getRemoteAddr(), contextRequest.getRemotePort());
            bodyRequest.trimToEmpty();
            try
            {
                asyncResponse.resume(new ResponseBase(0, callable.call(), "OK"));
                logger.info(String.format("RequestBase %s finished in %d ms", requestInfo, (System.currentTimeMillis() - t0)));
            }
            catch (WebBondException webBondException)
            {
                asyncResponse.resume(new ResponseBase(webBondException.getCode(), webBondException.getMessage()));
                logger.error("Got exception for request {}", requestInfo, webBondException);
            }
            catch (Exception e)
            {
                asyncResponse.resume(Response.status(Status.INTERNAL_SERVER_ERROR).build());
                logger.error("Got exception for request {}", requestInfo, e);
            }
        });
    }

    private void checkBrokers(RequestBase request, RedisLogonInfo logonInfo)
    {
        if (null == request.quoteBrokerIds)
        {
            request.quoteBrokerIds = logonInfo.authorizedBrokers;
        }
        else
        {
            request.quoteBrokerIds.retainAll(logonInfo.authorizedBrokers);
        }
        if (null == request.tradeBrokerIds)
        {
            request.tradeBrokerIds = logonInfo.authorizedBrokers;
        }
        else
        {
            request.tradeBrokerIds.retainAll(logonInfo.authorizedBrokers);
        }
    }

    private void updateRedisAndSendNotificationAsync(String token, RedisFilter redisFilter, RabbitMqMessage.Type type)
    {
        executorService.submit(() ->
        {
            try
            {
                redis.hsetex(config.getRedisFilter(), token, mapper.writeValueAsString(redisFilter), config.getRedisDataTimeout());
                notificationSender.send(mapper.writeValueAsBytes(new RabbitMqMessage(token, type)));
            }
            catch (JsonProcessingException e)
            {
                logger.error("got json process exception" + e);
            }
            catch (IOException e)
            {
                logger.error("got io exception" + e);
            }
        });
    }

    @POST
    @Path("/getdictionary")
    @Produces("application/json;charset=utf-8")
    public void getDictionary(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, GetDictionaryRequest request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            checkLogon(request);
            return repo.getDictionaryString();
        });
    }

    @POST
    @Path("/request")
    @Produces("application/json;charset=utf-8")
    public void filter(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, BondFilter request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            checkLogon(request);
            return repo.filterBonds(request);
        });
    }

    @POST
    @Path("/bondSearch")
    @Produces("application/json;charset=utf-8")
    public void bondSearch(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, BondSearchRequest request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            checkLogon(request);
            return repo.searchBonds(request);
        });
    }

    @POST
    @Path("/bestQuote")
    @Produces("application/json;charset=utf-8")
    public void bestQuote(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, ComplexFilter request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            RedisLogonInfo logonInfo = checkLogon(request);
            request.check();
            checkBrokers(request, logonInfo);
            Collection<String> bondKeyListedMarkets = repo.filterBonds(request);
            updateRedisAndSendNotificationAsync(request.getToken(), new RedisFilter(bondKeyListedMarkets, request.quoteBrokerIds, request.tradeBrokerIds), RabbitMqMessage.Type.BBOUPDATE);
            Collection<WebbondBbo> bbos = bboFilterTool.doFilter(bondKeyListedMarkets, repo.getSortedBboSet(), request, logonInfo.cdcAuthed);
            return cdcFilterTool.updateWebBbo(logonInfo.cdcAuthed, bbos);
        });
    }

    @POST
    @Path("/bestQuoteByBondKey")
    @Produces("application/json;charset=utf-8")
    public void bestQuoteByBondKey(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, FilterByBondRequest request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            RedisLogonInfo logonInfo = checkLogon(request);
            request.check();
            checkBrokers(request, logonInfo);
            Collection<String> bondKeyListedMarkets = Sets.newHashSet(request.bondKeyListedMarket);
            updateRedisAndSendNotificationAsync(request.getToken(), new RedisFilter(bondKeyListedMarkets, request.quoteBrokerIds, request.tradeBrokerIds), RabbitMqMessage.Type.BBOUPDATE);
            Collection<WebbondBbo> bbos = bboFilterTool.doFilter(bondKeyListedMarkets, repo.getSortedBboSet(), request);
            return cdcFilterTool.updateWebBbo(logonInfo.cdcAuthed, bbos);
        });
    }

    @POST
    @Path("/tradeToday")
    @Produces("application/json;charset=utf-8")
    public void tradeToday(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, ComplexFilter request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            RedisLogonInfo logonInfo = checkLogon(request);
            request.check();
            checkBrokers(request, logonInfo);
            Collection<String> bondKeyListedMarkets = repo.filterBonds(request);
            updateRedisAndSendNotificationAsync(request.getToken(), new RedisFilter(bondKeyListedMarkets, request.quoteBrokerIds, request.tradeBrokerIds), RabbitMqMessage.Type.TRADEUPDATE);
            Collection<WebbondTrade> trades = tradeFilterTool.doFilter(bondKeyListedMarkets, repo.getSortedTradeSet(), request);
            return cdcFilterTool.updateWebTrade(logonInfo.cdcAuthed, trades);
        });
    }

    @POST
    @Path("/tradeTodayByBondKey")
    @Produces("application/json;charset=utf-8")
    public void tradeTodayByBondKey(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, FilterByBondRequest request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            RedisLogonInfo logonInfo = checkLogon(request);
            request.check();
            checkBrokers(request, logonInfo);
            Collection<String> bondKeyListedMarkets = Sets.newHashSet(request.bondKeyListedMarket);
            updateRedisAndSendNotificationAsync(request.getToken(), new RedisFilter(bondKeyListedMarkets, request.quoteBrokerIds, request.tradeBrokerIds), RabbitMqMessage.Type.TRADEUPDATE);
            Collection<WebbondTrade> trades = tradeFilterTool.doFilter(bondKeyListedMarkets, repo.getSortedTradeSet(), request);
            return cdcFilterTool.updateWebTrade(logonInfo.cdcAuthed, trades);
        });
    }

    @POST
    @Path("/logon")
    @Produces("application/json;charset=utf-8")
    public void logon(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, LogonRequest request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            //TODO  return response immediately if redis has logon info
            String hexString = null;
            if (request.encrypted) {
                hexString = request.passWord;
            } else {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte messageDigest[] = md.digest(request.passWord.getBytes(StandardCharsets.UTF_8));
                hexString = Hex.encodeHexString(messageDigest);
            }
            Content content = null;
            try
            {
                content = Request.Post(config.getIamUrl() + config.getIamTokenPath() + "?" + String.format("grant_type=password&username=%s&password=%s&scope=account", request.userName, hexString))
                        .setHeader("Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString(String.format("%s:%s", config.getClientId(), config.getClientSecret()).getBytes()))).
                                setHeader("content-type", ContentType.APPLICATION_FORM_URLENCODED.toString()).execute().returnContent();
                logger.info("got IAM response: {}", content);
            }
            catch (HttpResponseException ex)
            {
                logger.error("got exception from IAM", ex);
                throw WebBondException.PredefinedExceptions.LogonFailed;
            }
            HashMap<String, String> map = mapper.readValue(content.asString(), TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
            if (!map.containsKey("error"))
            {
                RedisLogonInfo redisLogonInfo = mapper.readValue(content.asString(), RedisLogonInfo.class);
                if (repo.getUserAccounts().containsKey(request.userName))
                {
                    UserAccount account = repo.getUserAccounts().get(request.userName);
                    redisLogonInfo.authorizedBrokers = account.authedBrokers;
                    redisLogonInfo.cdcAuthed = account.cdcAuthed;
                }
                try
                {
                    redis.hsetex(config.getRedisLogon(), redisLogonInfo.accessToken, mapper.writeValueAsString(redisLogonInfo), redisLogonInfo.expiresIn);
                    byte[] bytes = mapper.writeValueAsBytes(new RabbitMqMessage(redisLogonInfo.accessToken, RabbitMqMessage.Type.LOGON));
                    notificationSender.send(bytes);
                    logger.info("Send mq message: {}", new String(bytes));
                }
                catch (Exception e)
                {
                    logger.error("got exception", e);
                }
                finally
                {
                    return new LogonResponse(redisLogonInfo.accessToken,
                            repo.getUserAccounts().containsKey(request.userName) ? repo.getUserAccounts().get(request.userName).displayName : "",
                            redisLogonInfo.authorizedBrokers,
                            redisLogonInfo.cdcAuthed,
                            "logon success");
                }
            }
            else
            {
                return WebBondException.PredefinedExceptions.LogonFailed;
            }
        });
    }

    @POST
    @Path("/logout")
    @Produces("application/json;charset=utf-8")
    public void logout(@Suspended AsyncResponse asyncResponse, @Context org.glassfish.grizzly.http.server.Request contextRequest, RequestBase request)
    {
        doRequest(contextRequest, asyncResponse, request, () ->
        {
            RedisLogonInfo logonInfo = checkLogon(request);
            try
            {
                redis.hsetex(config.getRedisLogon(), request.getToken(), "", logonInfo.expiresIn);
                byte[] bytes = mapper.writeValueAsBytes(new RabbitMqMessage(request.getToken(), RabbitMqMessage.Type.LOGOUT));
                notificationSender.send(bytes);
                logger.info("Send mq message: {}", new String(bytes));
                return new ResponseBase(0, null, "OK");
            }
            catch (Exception e)
            {
                logger.error("got exception ", e);
                throw WebBondException.PredefinedExceptions.LogoutFailed;
            }
        });
    }

}

