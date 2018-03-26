package com.sumscope.qpwb.ncd.utils;

import com.alibaba.fastjson.JSON;
import com.sumscope.qpwb.ncd.domain.BrokerDTO;
import com.sumscope.qpwb.ncd.global.exception.NcdExceptionModel;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liu.yang on 2018/1/10.
 */
@Component
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    @Value("${application.constents.brokers}")
    private String brokerListStr;
    private List<BrokerDTO> brokerList;
    private Map<String, String> brokerMap = new HashedMap();
    private List<String> brokers = new ArrayList<>();

    @PostConstruct
    public void init() {
        brokerList = new ArrayList<>();
        if (!StringUtils.isBlank(brokerListStr)) {
            String[] split = brokerListStr.split("\\|");
            for (String b : split) {
                BrokerDTO brokerDTO = new BrokerDTO();
                String[] brokerIdName = b.split(":");
                if (brokerIdName.length < 2) {
                    LOGGER.error("the broker config is error");
                    continue;
                }
                String brokerId = brokerIdName[0];
                String brokerName = brokerIdName[1];
                brokerDTO.setKey(brokerId);
                brokerDTO.setName(brokerName);
                brokerList.add(brokerDTO);
                brokerMap.put(brokerId, brokerName);
                brokers.add(brokerId);
            }
            LOGGER.info("load broker list [{}]", JSON.toJSONString(brokerList));
        } else {
            throw NcdExceptionModel.InitFailed;
        }
    }

    public int getRadom(int seed) {
        if (seed < 1) return 0;
        Random random = new Random();
        return random.nextInt(seed) + 1;
    }

    public String getDateTimeByFormat(String format) {
        try {
            return new SimpleDateFormat(format).format(new Date());
        } catch (Exception e) {
            LOGGER.error("get DateTimeByFormat error. format = {}  exception = {}", format, e.getMessage());
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
    }

    private String getBorkerName(String brokerId) {
        if (brokerId != null) {
            switch (brokerId) {
                case "1":
                    return "国利";
                case "2":
                    return "国际";
                case "3":
                    return "中诚";
                case "4":
                    return "平安";
                case "5":
                    return "信唐";
                default:
                    LOGGER.error("no match brokerId = {}", brokerId);
                    return "其它";
            }
        } else {
            LOGGER.error("brokerId can not be null");
            return "其它";
        }
    }

    public List<BrokerDTO> getBrokerList(Collection<String> brokerIds) {
        return brokerList.stream().filter(x -> brokerIds.contains(x.getKey())).collect(Collectors.toList());
    }

    public List<BrokerDTO> getAllBrokerList() {
        return brokerList;
    }

    public Map<String, String> getBrokerMap() {
        return brokerMap;
    }

    public List<String> getBrokerIds() {
        return brokers;
    }
}
