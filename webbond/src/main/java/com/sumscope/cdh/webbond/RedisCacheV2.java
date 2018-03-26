package com.sumscope.cdh.webbond;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.PatternMessageListener;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisCacheV2 implements AutoCloseable, IPubSub
{

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheV2.class);

    private RedissonClient redisClient;

    @Autowired
    private Config config;

    @PostConstruct
    private void open()
    {
        String hostAndPort = String.format("%s:%d", config.getRedisHost(), config.getRedisPort());
        org.redisson.config.Config redisConfig = new org.redisson.config.Config();
        if (config.getRedisCluster())
        {
            redisConfig.useClusterServers().addNodeAddress(hostAndPort);
        }
        else
        {
            redisConfig.useSingleServer().setAddress(hostAndPort);
        }
        redisConfig.setCodec(StringCodec.INSTANCE);
        redisClient = Redisson.create(redisConfig);
        logger.info("Redis connection established: " + hostAndPort);
    }

    ////////////////////////////////////////////////////////////////
    //	keys													  //
    ////////////////////////////////////////////////////////////////
    public Collection<String> getKeys(String pattern)
    {
        RKeys keys = redisClient.getKeys();
        return keys.findKeysByPattern(pattern);
    }

    public Future<Collection<String>> getKeysAsync(String pattern)
    {
        RKeys keys = redisClient.getKeys();
        return keys.findKeysByPatternAsync(pattern);
    }

    public long delKeys(String pattern)
    {
        RKeys keys = redisClient.getKeys();
        return keys.deleteByPattern(pattern);
    }

    public Future<Long> delKeysAsync(String pattern)
    {
        RKeys keys = redisClient.getKeys();
        return keys.deleteByPatternAsync(pattern);
    }

    ////////////////////////////////////////////////////////////////
    //	get/set													  //
    ////////////////////////////////////////////////////////////////
    public String get(String key)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        return bucket.get();
    }

    public Future<String> getAsync(String key)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        return bucket.getAsync();
    }

    public void set(String key, String value)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        bucket.set(value);
    }

    public Future<Void> setAsync(String key, String value)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        return bucket.setAsync(value);
    }

    /* in second */
    public void set(String key, String value, int expire)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        bucket.set(value, expire, TimeUnit.SECONDS);
    }

    /* in second */
    public Future<Void> setAsync(String key, String value, int expire)
    {
        RBucket<String> bucket = redisClient.getBucket(key);
        return bucket.setAsync(value, expire, TimeUnit.SECONDS);
    }

    ////////////////////////////////////////////////////////////////
    //	mget/mset												  //
    ////////////////////////////////////////////////////////////////
    public Map<String, Future<String>> mgetAsync(Set<String> keys)
    {
        return keys.stream()
                .collect(Collectors.toMap(
                        x -> x,
                        x ->
                        {
                            RBucket<String> bucket = redisClient.getBucket(x);
                            return bucket.getAsync();
                        }
                ));
    }

    public Future<Void> msetAsync(Map<String, String> keyValues)
    {
        List<Future<Void>> futures = keyValues.entrySet().stream()
                .map(x ->
                {
                    RBucket<String> bucket = redisClient.getBucket(x.getKey());
                    return bucket.setAsync(x.getValue());
                }).collect(Collectors.toList());
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                for (Future<Void> f : futures)
                {
                    f.get();
                }
            }
            catch (InterruptedException | ExecutionException e)
            {
                /* ignore */
            }
            return null;
        });
    }

    ////////////////////////////////////////////////////////////////
    //	hget/hset												  //
    ////////////////////////////////////////////////////////////////
    public String hget(String hash, String key)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        return map.get(key);
    }

    public Future<String> hgetAsync(String hash, String key)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        return map.getAsync(key);
    }

    public void hset(String hash, String key, String value)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        map.fastPut(key, value);
        logger.debug("put {} <key,value>=<{},{}>", hash, key, value);
    }

    public Future<Void> hsetAsync(String hash, String key, String value)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        //return map.fastPutAsync(key, value);
		/* convert Future<Boolean> to Future<Void> */
        return CompletableFuture.supplyAsync(() ->
        {
            try
            {
                map.fastPutAsync(key, value).get();
            }
            catch (InterruptedException | ExecutionException e)
            {
				/* ignore */
            }
            return null;
        });
    }

    ////////////////////////////////////////////////////////////////
    //	hmget/hmset												  //
    ////////////////////////////////////////////////////////////////
    public Map<String, String> hmget(String hash, Set<String> keys)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        return map.getAll(keys);
    }

    public Future<Map<String, String>> hmgetAsync(String hash, Set<String> keys)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        return map.getAllAsync(keys);
    }

    public void hmset(String hash, Map<String, String> keyValues)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        map.putAll(keyValues);
    }

    public Future<Void> hmsetAsync(String hash, Map<String, String> keyValues)
    {
        RMap<String, String> map = redisClient.getMap(hash);
        return map.putAllAsync(keyValues);
    }

    ////////////////////////////////////////////////////////////////
    //	publish/subscribe										  //
    ////////////////////////////////////////////////////////////////
    @Override
    public void publish(String channel, String message)
    {
        RTopic<String> topic = redisClient.getTopic(channel);
        topic.publish(message);
    }

    @Override
    public void subscribe(ISubListener listener, String channel)
    {
        RTopic<String> topic = redisClient.getTopic(channel);
        topic.addListener(new MessageListener<String>()
        {
            @Override
            public void onMessage(String channel, String message)
            {
                listener.OnMessage(channel, message);
            }
        });
    }

    @Override
    public void psubscribe(ISubListener listener, String pattern)
    {
        RPatternTopic<String> topic = redisClient.getPatternTopic(pattern);
        topic.addListener(new PatternMessageListener<String>()
        {
            @Override
            public void onMessage(String pattern, String channel, String message)
            {
                listener.OnMessage(channel, message);
            }
        });
    }

    @Override
    public void close() throws Exception
    {
        if (redisClient != null)
        {
            redisClient.shutdown();
        }
    }

    public void hsetex(String hash, String key, String value, int expire) {
        RMapCache<String, String> map = redisClient.getMapCache(hash);
        map.fastPut(key, value, expire, TimeUnit.SECONDS);
        logger.debug("put {} <key,value>=<{},{}>", hash, key, value);
    }

    public Future<Void> hsetexAsync(String hash, String key, String value, int expire) {
        RMapCache<String, String> map = redisClient.getMapCache(hash);
        // return map.fastPutAsync(key, value);
        /* convert Future<Boolean> to Future<Void> */
        return CompletableFuture.supplyAsync(() -> {
            try {
                map.fastPutAsync(key, value, expire, TimeUnit.SECONDS).get();
            } catch (InterruptedException | ExecutionException e) {
                /* ignore */
            }
            return null;
        });
    }

    public String hgetex(String hash, String key) {
        RMapCache<String, String> map = redisClient.getMapCache(hash);
        return map.get(key);
    }

    public Future<String> hgetexAsync(String hash, String key) {
        RMapCache<String, String> map = redisClient.getMapCache(hash);
        return map.getAsync(key);
    }

}
