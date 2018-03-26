package com.sumscope.cdh.webbond.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by chengzhang.wang on 2017/2/14.
 */
public class RestLoadTool
{
    static final Logger logger = LoggerFactory.getLogger("com.sumscope.cdh.webbond");

    public <K, V> void loadData(Class<V> tClass, String apiKey, PrimaryKeyGenerator<K, V> pkg, Map<K, V> map, ICdhRestfulConfig restfulConfig, PropertyNamingStrategy propertyNamingStrategy) throws IOException
    {
        CdhRestful.query(restfulConfig, apiKey, (arrayNodes) ->
        {
            ObjectMapper mapper = JsonTool.createObjectMapper();
            mapper.setPropertyNamingStrategy(propertyNamingStrategy);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            for (JsonNode node : arrayNodes)
            {
                try
                {
                    V t = mapper.treeToValue(node, tClass);
                    map.put(pkg.generatePrimaryKey(t), t);
                }
                catch (Exception e)
                {
                    logger.error("Load {} error", apiKey, e);
                }
            }
            logger.info("Load {} with count {}", apiKey, arrayNodes.size());
        });
    }

    public <K, V> void loadData(Class<V> tClass, String apiKey, PrimaryKeyGenerator<K, V> pkg, Map<K, V> map, ICdhRestfulConfig restfulConfig) throws IOException
    {
        loadData(tClass, apiKey, pkg, map, restfulConfig, PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    public <T> void loadSet(Class<T> tClass, String apiKey, Set<T> set, ICdhRestfulConfig restfulConfig) throws IOException
    {
        CdhRestful.query(restfulConfig, apiKey, (arrayNodes) ->
        {
            ObjectMapper mapper = JsonTool.createObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            for (JsonNode node : arrayNodes)
            {
                try
                {
                    T t = mapper.treeToValue(node, tClass);
                    if (!set.add(t))
                    {
                        logger.error("add to set failed for {}", mapper.writeValueAsString(t));
                    }
                    ;
                }
                catch (Exception e)
                {
                    logger.error("Load {} error", apiKey, e);
                }
            }
            logger.info("Load {} with count {}", apiKey, arrayNodes.size());
        });
    }
}
