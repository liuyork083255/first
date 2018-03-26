package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    Orders findByQuoteDetailIdAndUserIdAndBrokerIdAndIssuerId(Long quoteId, String userId, String brokerId, String issuerId);
}
