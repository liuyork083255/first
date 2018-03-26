package com.sumscope.qpwb.ncd.data.service;

import com.sumscope.qpwb.ncd.data.model.db.StateOwnBank;
import com.sumscope.qpwb.ncd.data.model.repository.StateOwnBankRepo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StateOwnBankService {
    @PostConstruct
    public void init() {
        loadAllStateOwnBank();
    }

    @Autowired
    StateOwnBankRepo bankRepo;
    public static final List<String> stateOwnBank = new ArrayList<>();
    public static final Map<String, String> stateOwnBankMap = new HashedMap();


    public void loadAllStateOwnBank() {
        List<StateOwnBank> allOwnBanks = bankRepo.findAll().stream().sorted(Comparator.comparing(
                StateOwnBank::getAutoId)).collect(Collectors.toList());
        allOwnBanks.forEach(o -> {
            stateOwnBankMap.put(o.getCode(), o.getSortValue());
            stateOwnBank.add(o.getCode());
        });
    }
}
