package com.sumscope.cdh.webbond;

import static com.sumscope.cdh.webbond.MDCLogger.APINAME;
import static com.sumscope.cdh.webbond.MDCLogger.MSG_DETAIL;

import java.io.IOException;
import java.net.URI;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import com.sumscope.cdh.webbond.utils.JsonTool;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumscope.cdh.sumscopezk4j.ZKnotice;
import com.sumscope.cdh.webbond.WebBondException.PredefinedExceptions;
import com.sumscope.cdh.webbond.model.ResponseBase;
import com.sumscope.cdh.webbond.utils.Utils;

@Provider
class MyObjectMapperContextResolver implements ContextResolver<ObjectMapper>
{

    private ObjectMapper mapper;

    public MyObjectMapperContextResolver()
    {
        mapper = JsonTool.createObjectMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //mapper.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
        //mapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
    }

    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        return mapper;
    }

}

class MyJsonProcessingExceptionMapper<E extends JsonProcessingException> implements ExceptionMapper<E>
{

    private static final Logger logger = LoggerFactory.getLogger(MyJsonProcessingExceptionMapper.class);

    @Context
    private javax.inject.Provider<Request> requestProvider;

    @Override
    public javax.ws.rs.core.Response toResponse(E e)
    {
        Request request = requestProvider.get();
        String requestUri = request.getRequestURI();
        if (requestUri.isEmpty() || requestUri.toLowerCase().indexOf("script") != -1)
        {
            javax.ws.rs.core.Response response = javax.ws.rs.core.Response.status(Status.BAD_REQUEST)
                    .build();
            logger.info("Invalid request uri");
            return response;
        }
        Response response = Response.status(Status.BAD_REQUEST)
                .entity(new ResponseBase(PredefinedExceptions.InvalidJsonRequest.getCode(), PredefinedExceptions.InvalidJsonRequest.getMessage()))
                .build();
        String requestInfo = String.format("%s(%s:%d)", request.getRequestURI(), request.getRemoteAddr(), request.getRemotePort());
        MDCLogger.set(APINAME, "JSON");
        MDCLogger.set(MSG_DETAIL, Utils.escape(ExceptionUtils.getStackTrace(e)));
        MDCLogger.getLogger().error(String.format("RequestBase %s failed: %d - %s", requestInfo, PredefinedExceptions.InvalidJsonRequest.getCode(), PredefinedExceptions.InvalidJsonRequest.getMessage()));
        return response;
    }

}

@Priority(Priorities.ENTITY_CODER)
@Provider
class MyJsonParseExceptionMapper extends MyJsonProcessingExceptionMapper<JsonParseException>
{

}

@Priority(Priorities.ENTITY_CODER)
@Provider
class MyJsonMappingExceptionMapper extends MyJsonProcessingExceptionMapper<JsonMappingException>
{

}

/*
 * Seems not working with browsers:
 * If User-Agent contains "Gecko" or "Trident", GZip is not used.
 */
@Priority(Priorities.HEADER_DECORATOR)
@Provider
class MyGZipInterceptor implements ReaderInterceptor, WriterInterceptor
{

    @Context
    private javax.inject.Provider<Request> requestProvider;

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException
    {
        String header = requestProvider.get().getHeader("Content-Encoding");
        if (header != null && header.contains("gzip"))
        {
            context.setInputStream(new GZIPInputStream(context.getInputStream()));
        }
        return context.proceed();
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException
    {
        String header = requestProvider.get().getHeader("Accept-Encoding");
        if (header != null && header.contains("gzip"))
        {
            context.setOutputStream(new GZIPOutputStream(context.getOutputStream()));
            context.getHeaders().add("Content-Encoding", "gzip");
        }
        context.proceed();
    }

}

@PreMatching
@Provider
class MyPreMatchingRequestFilter implements ContainerRequestFilter
{

    private static final Logger logger = LoggerFactory.getLogger(MyPreMatchingRequestFilter.class);

    @Context
    private javax.inject.Provider<Request> requestProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        Request request = requestProvider.get();
        logger.info(String.format("RequestBase %s from %s:%d", request.getRequestURI(), request.getRemoteAddr(), request.getRemotePort()));
    }

}

/*
 * TODO:
 * 1. trailing comma.
 * 2. upper case in response.
 */

@SpringBootApplication
public class App
{

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final String ROOT_PATH = "webbond";

    public static void main(String[] args)
    {
        try
        {
            MDCLogger.clear();
            SpringApplication app = new SpringApplication(SpringConfiguration.class);
            ConfigurableApplicationContext appContext = app.run(args);
            Config config = appContext.getBean(Config.class);
            try
            {
                new ZKnotice(config.getZookeeperConnection()).notice(config.getServiceName());
            }
            catch (Exception e)
            {
                logger.warn(String.format(
                        "Failed to connect to zookeeper %s, stacktrace: %s", config.getZookeeperConnection(), ExceptionUtils.getStackTrace(e)));
            }
            final ResourceConfig resourceConfig = new ResourceConfig();
            resourceConfig.register(new ContainerRequestFilter()
            {
                @Override
                public void filter(ContainerRequestContext requestContext) throws IOException
                {
                    if (requestContext.getMethod().equals("POST") && !requestContext.hasEntity())
                    {
                        requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                                .entity(new ResponseBase(PredefinedExceptions.EmptyRequestBody.getCode(), PredefinedExceptions.EmptyRequestBody.getMessage()))
                                .build());
                    }
                }
            });
            resourceConfig.register(new ContainerResponseFilter()
            {
                @Override
                public void filter(ContainerRequestContext request,
                                   ContainerResponseContext response) throws IOException
                {
                    response.getHeaders().add("Access-Control-Allow-Origin", "*");
                    response.getHeaders().add("Access-Control-Allow-Headers",
                            "origin, content-type, accept, authorization");
                    response.getHeaders().add("Access-Control-Allow-Credentials", "true");
                    response.getHeaders().add("Access-Control-Allow-Methods",
                            "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                    response.getHeaders().add("Access-Control-Max-Age", 1728000);
                }
            });
            resourceConfig.register(Main.class);
            resourceConfig.register(MyObjectMapperContextResolver.class); /* case insensitive */
            resourceConfig.register(MyJsonParseExceptionMapper.class); /* json parse */
            resourceConfig.register(MyJsonMappingExceptionMapper.class); /* json mapping */
            if (config.getGzipEnabled())
            {
                resourceConfig.register(MyGZipInterceptor.class);
            }
            //resourceConfig.register(MyPreMatchingRequestFilter.class); /* request logging */
            resourceConfig.property("contextConfig", appContext); /* spring context */
            URI baseUri = URI.create(String.format("http://%s:%d/", config.getHost(), config.getPort()));
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig, false);
//			server.getServerConfiguration().addHttpHandler();
            Runtime.getRuntime().addShutdownHook(new Thread(() ->
            {
                server.shutdownNow();
            }));
            server.start();
            System.out.println(String.format("Application started.\nTry out %s%s", baseUri, ROOT_PATH));
            Thread.currentThread().join();
        }
        catch (IOException | InterruptedException e)
        {
            logger.error(null, e);
            System.exit(-1);
        }
    }

}
