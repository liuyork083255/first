package com.sumscope.cdh.realtime;

import com.lmax.disruptor.dsl.Disruptor;
import com.sumscope.cdh.realtime.model.db.BondModel;
import com.sumscope.cdh.realtime.recevier.QpidReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Created by liu.yang on 2017/10/18.
 */
@Component
public class RealtimeShSzToRabbitmqStart {

    @Autowired
    private QpidReceiver qpidReceiver;
    @Autowired
    private Disruptor<BondModel> disruptor;

    public void start(){
        disruptor.start();
        qpidReceiver.start();
    }

    @PreDestroy
    public void close(){
        qpidReceiver.close();
        disruptor.shutdown();
    }

    public static void main(String[] args) {
        SpringApplication.run(RealtimeShQpidToRabbitmqApplication.class,args).
                getBean(RealtimeShSzToRabbitmqStart.class).start();
    }
}
