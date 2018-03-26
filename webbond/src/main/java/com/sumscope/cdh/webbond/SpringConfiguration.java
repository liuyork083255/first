package com.sumscope.cdh.webbond;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.sumscope.cdh.sumscopezk4j.support.spring.ZooKeeperPropertyPlaceholderConfigurer;

@Configuration
@ComponentScan
public class SpringConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);

	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer configurer;
        String connect = System.getProperty("spring.zookeeper.connect");
        String paths = System.getProperty("spring.zookeeper.path");
        if (connect != null && paths != null) {
        	logger.info(String.format("Using zookeeper config: connect=%s paths=%s", connect, paths));
            ZooKeeperPropertyPlaceholderConfigurer zc = new ZooKeeperPropertyPlaceholderConfigurer();
            zc.setConnectString(connect);
            zc.setPath(StringUtils.split(paths, ","));
            configurer = zc;
        } else {
            configurer = new PropertyPlaceholderConfigurer();
            String configs = System.getProperty("spring.config.name");
            if (configs != null) {
            	logger.info("Using local config: " + configs);
                String[] strs = StringUtils.split(configs, ",");
                ClassPathResource[] configFiles = new ClassPathResource[strs.length];
                for (int i = 0; i < strs.length; i++) {
                    configFiles[i] = new ClassPathResource(strs[i]);
                }
                configurer.setLocations(configFiles);
            } else {
            	logger.info("Using default local config...");
            	configurer.setLocation(new ClassPathResource("application.properties"));
            }
        }
        configurer.setIgnoreResourceNotFound(true);
        configurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        return configurer;
	}

}
