package com.sumscope.cdh.webbond;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.sumscope.cdh.webbond.utils.ICdhRestfulConfig;
import com.sumscope.cdh.webbond.utils.IQpidConfig;
import com.sumscope.cdh.webbond.utils.Utils;

@Component
public class Config implements ICdhRestfulConfig, IQpidConfig
{

    @Value("${application.serviceName}")
    private String serviceName;

    @Value("${application.host}")
    private String host;

    @Value("${application.port}")
    private int port;

    @Value("${application.expiredSeconds}")
    private int expiredSeconds;

    @Value("${application.maxExpiredSeconds}")
    private int maxExpiredSeconds;

    @Value("${application.threadPoolSize}")
    private int threadPoolSize;

    @Value("${application.gzipEnabled}")
    private boolean gzipEnabled;

    @Value("${application.zookeeper.connection}")
    private String zookeeperConnection;

    @Value("${application.redis.host}")
    private String redisHost;

    @Value("${application.redis.port}")
    private int redisPort;

    @Value("${application.redis.cluster}")
    private boolean redisCluster;

    @Value("${application.redis.data.timeout}")
    private int redisDataTimeout;

    @Value("${application.iam.url}")
    private String iamUrl;

    @Value("${application.iam.tokenPath}")
    private String iamTokenPath;

    @Value("${application.iam.authorizePath}")
    private String iamAuthorizePath;

    @Value("${application.iam.tokenCheckPath}")
    private String iamTokenCheckPath;

    @Value("${application.iam.tokenRetirePath}")
    private String iamTokenRetirePath;

    @Value("${application.iam.apiAuthorizePath}")
    private String iamApiAuthorizePath;

    @Value("${application.iam.clientId}")
    private String clientId;

    @Value("${application.iam.clientSecret}")
    private String clientSecret;

    @Value("${application.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${application.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${application.rabbitmq.outputExchangeName}")
    private String rabbitmqOutputExchangeName;

    @Value("${application.rabbitmq.outputExchangeType}")
    private String rabbitmqOutputExchangeType;

    @Value("${application.rabbitmq.bboInputExchangeName}")
    private String rabbitmqBboInputExchangeName;

    @Value("${application.rabbitmq.bboInputExchangeType}")
    private String rabbitmqBboInputExchangeType;

    @Value("${application.rabbitmq.tradeFilterInputExchangeName}")
    private String rabbitmqTradeFilterInputExchangeName;

    @Value("${application.rabbitmq.tradeFilterInputExchangeType}")
    private String rabbitmqTradeFilterInputExchangeType;

    @Value("${application.redis.data.logon}")
    private String redisDataLogon;

    @Value("${application.redis.data.filter}")
    private String redisDataFilter;

    @PostConstruct
    public void init()
    {
        serviceName = String.format("%s_%s:%d", serviceName, Utils.getLocalHost(), port);
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public int getThreadPoolSize()
    {
        return threadPoolSize;
    }

    public boolean getGzipEnabled()
    {
        return gzipEnabled;
    }

    public String getZookeeperConnection()
    {
        return zookeeperConnection;
    }

    public String getRedisHost()
    {
        return redisHost;
    }

    public int getRedisPort()
    {
        return redisPort;
    }

    public boolean getRedisCluster()
    {
        return redisCluster;
    }

    @Value("${application.restful.qpid.connectionfactory}")
    private String qpidFactory;

    @Override
    public String getQpidFactory()
    {
        return qpidFactory;
    }

    @Value("${application.restful.qpid.destination}")
    private String qpidAddress;

    @Override
    public String getQpidAddress()
    {
        return qpidAddress;
    }

    @Value("${application.restful.qpid.reconnect_count}")
    private int qpidReconnectCount;

    @Override
    public int getQpidReconnectCount()
    {
        return qpidReconnectCount;
    }

    @Value("${application.restful.qpid.reconnect_interval_ms}")
    private int qpidReconnectInterval;

    @Override
    public int getQpidReconnectInterval()
    {
        return qpidReconnectInterval;
    }

    @Value("${application.restful.host}")
    private String restfulHost;

    @Override
    public String getRestfulHost()
    {
        return restfulHost;
    }

    @Value("${application.restful.port}")
    private int restfulPort;

    @Override
    public int getRestfulPort()
    {
        return restfulPort;
    }

    @Value("${application.restful.user}")
    private String restfulUser;

    @Override
    public String getRestfulUser()
    {
        return restfulUser;
    }

    @Value("${application.restful.password}")
    private String restfulPassword;

    @Override
    public String getRestfulPassword()
    {
        return restfulPassword;
    }

    @Value("${application.restful.page_size}")
    private int restfulPageSize;

    @Override
    public int getRestfulPageSize()
    {
        return restfulPageSize;
    }

    @Autowired
    private ApplicationContext ctx;

    @Override
    public String getRestfulApiJson(String propertyKey)
    {
        return ((ConfigurableApplicationContext)ctx).getBeanFactory().resolveEmbeddedValue(
                "${" + propertyKey + "}");
    }

    public String getIamUrl()
    {
        return iamUrl;
    }

    public String getIamTokenPath()
    {
        return iamTokenPath;
    }

    public String getClientId()
    {
        return clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public String getRabbitmqHost()
    {
        return rabbitmqHost;
    }

    public int getRabbitmqPort()
    {
        return rabbitmqPort;
    }

    public String getRabbitmqOutputExchangeName()
    {
        return rabbitmqOutputExchangeName;
    }

    public String getRabbitmqOutputExchangeType()
    {
        return rabbitmqOutputExchangeType;
    }

    public String getRedisLogon()
    {
        return redisDataLogon;
    }

    public String getRedisFilter()
    {
        return redisDataFilter;
    }

    public int getRedisDataTimeout()
    {
        return redisDataTimeout;
    }

    public String getRabbitmqBboInputExchangeName()
    {
        return rabbitmqBboInputExchangeName;
    }

    public String getRabbitmqBboInputExchangeType()
    {
        return rabbitmqBboInputExchangeType;
    }

    public String getRabbitmqTradeFilterInputExchangeName()
    {
        return rabbitmqTradeFilterInputExchangeName;
    }

    public String getRabbitmqTradeFilterInputExchangeType()
    {
        return rabbitmqTradeFilterInputExchangeType;
    }

}
