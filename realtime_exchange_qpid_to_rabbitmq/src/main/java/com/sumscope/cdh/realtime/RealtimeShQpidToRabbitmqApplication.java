package com.sumscope.cdh.realtime;

import com.alibaba.druid.util.StringUtils;
import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.handler.*;
import com.sumscope.cdh.realtime.model.handler.BondEventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class RealtimeShQpidToRabbitmqApplication {

	private  static final Logger LOGGER = LoggerFactory.getLogger(RealtimeShQpidToRabbitmqApplication.class);

	@Value("${logging.level.write.log}")
	private String isWriteLog;
	@Autowired
	private Environment configParam;
	@Autowired
	private Disruptor<BondEventModel> disruptor;
	@Autowired
	private DBHandler dbHandler;
	@Autowired
	private ResetHandler resetHandler;
	@Autowired
	private FilterHandler filterHandler;
	@Autowired
	private SenderHandler senderHandler;
	@Autowired
	private UpdateFieldHandler updateFieldHandler;
	@Autowired
	private SenderWriteLogHandler senderWriteLogHandler;

	@PostConstruct
	public void init(){
		String flag = configParam.getProperty("mysql.save.bond");
		if("true".equals(flag))
		{
			if(StringUtils.equals("true",isWriteLog)){
				disruptor.handleEventsWith(filterHandler).then(updateFieldHandler).then(senderWriteLogHandler,dbHandler).then(resetHandler);
				LOGGER.info("write detail log pattern");
			}else{
				disruptor.handleEventsWith(filterHandler).then(updateFieldHandler).then(senderHandler,dbHandler).then(resetHandler);
			}

			LOGGER.info("mysql save bonds pattern.");
		}
		else if("false".equals(flag))
		{
			if(StringUtils.equals("true",isWriteLog)){
				disruptor.handleEventsWith(filterHandler).then(updateFieldHandler).then(senderWriteLogHandler).then(resetHandler);
				LOGGER.info("write detail log pattern");
			}else{
				disruptor.handleEventsWith(filterHandler).then(updateFieldHandler).then(senderHandler).then(resetHandler);
			}
			LOGGER.info("mysql do not save bonds pattern.");
		}
		else
		{
			throw new RuntimeException("Please configure whether to save the bonds.  For reference [mysql.save.bond=true or false]");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RealtimeShQpidToRabbitmqApplication.class, args);
	}
}
