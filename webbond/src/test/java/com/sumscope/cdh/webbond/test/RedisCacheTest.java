package com.sumscope.cdh.webbond.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;

import com.sumscope.cdh.webbond.RedisCacheV2;
import com.sumscope.cdh.webbond.Config;

@Configuration
@PropertySource(value="application.properties")
class RedisCacheTestConfig {
    @Bean
    Config config(){ return new Config();}

    @Bean
    RedisCacheV2 redisCacheV2(){ return new RedisCacheV2();}
}


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisCacheTestConfig.class)
public class RedisCacheTest {

	private static Logger logger = LoggerFactory.getLogger(RedisCacheTest.class);

	@Autowired
	private RedisCacheV2 redis;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Ignore
	@Test
	public void testSetGet() throws Exception {
		redis.set("testkey1", "testvalue1", 120);
		String value1 = redis.get("testkey1");
		assertThat(value1, is("testvalue1"));
		Future<Void> f1 = redis.setAsync("testkey2", "testvalue2", 120);
		f1.get();
		Future<String> f2 = redis.getAsync("testkey2");
		String value2 = f2.get();
		assertThat(value2, is("testvalue2"));
	}

	//@Ignore
	@Test
	public void testPubSub() throws Exception {
		/* subscribe */
		redis.subscribe((channel, message) -> {
			logger.info(String.format("get message channel=%s, message=%s", channel, message));
		}, "testpubsub");
		redis.publish("testpubsub", "testpubsubvalue");
		/* psubscribe */
		redis.psubscribe((channel, message) -> {
			logger.info(String.format("get message channel=%s, message=%s", channel, message));
		}, "testpubsub.*");
		redis.publish("testpubsub.1", "testpubsubvalue.1");
		redis.publish("testpubsub.2", "testpubsubvalue.2");
		logger.info(String.format("published two messages"));
		Thread.sleep(3*1000);
	}

}
