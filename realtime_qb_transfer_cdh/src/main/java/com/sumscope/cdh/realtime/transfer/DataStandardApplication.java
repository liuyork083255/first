package com.sumscope.cdh.realtime.transfer;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.transfer.handler.*;
import com.sumscope.cdh.realtime.transfer.model.handler.SourceEventModel;
import com.sumscope.cdh.realtime.transfer.model.handler.TargetEventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class DataStandardApplication {

	@Autowired
	private SourceToDBHandler sourceToDBHandler;
	@Autowired
	private SourceResetHandler sourceResetHandler;
	@Autowired
	private SourceStringConvertModelHandler sourceStringConvertModelHandler;
	@Autowired
	private SourceModelToTargetModelHandler sourceModelToTargetModelHandler;
	@Autowired
	private TargetToDBHandler targetToDBHandler;
	@Autowired
	private TargetResetHandler targetResetHandler;
	@Autowired
	private TargetSenderToMQHandler targetSenderToMQHandler;

	@Autowired
	@Qualifier("SourceDisruptor")
	private Disruptor<SourceEventModel> sourceEventModelDisruptor;
	@Autowired
	@Qualifier("TargetDisruptor")
	private Disruptor<TargetEventModel> targetEventModelDisruptor;

	@PostConstruct
	public void init(){
		sourceEventModelDisruptor.handleEventsWith(sourceStringConvertModelHandler).then(sourceToDBHandler,sourceModelToTargetModelHandler).then(sourceResetHandler);
		targetEventModelDisruptor.handleEventsWith(targetToDBHandler,targetSenderToMQHandler).then(targetResetHandler);
	}

	public static void main(String[] args) {
		SpringApplication.run(DataStandardApplication.class, args);
	}
}
