package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.OrderDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails, String> {
//    @Query(value = "select distinct o.id,o.issuer_name,o.user_name,o.customer_org_name,q.terminal,q.issue_price,o.create_time from orders o,quote_details q,broker_admin b\n" +
//            "where EXISTS (select broker_id from broker_admin where user_id = ?1 and o.broker_id = broker_id) and o.user_id = ?1 and o.quote_detail_id = q.id", nativeQuery = true)
//    List<OrderDetails> findOrderDetailsByUserId(String userId);

    @Query(value = "select distinct o.id,o.issuer_name,o.user_id,q.terminal,q.issue_price," +
            "CASE WHEN q.price_type IS NULL THEN TRUE WHEN price_type = '0' THEN TRUE ELSE FALSE END as `price_type`," +
            "o.create_time from orders o,quote_details q\n" +
            "where o.broker_id = ? and o.quote_detail_id = q.id ORDER by o.create_time DESC", nativeQuery = true)
    List<OrderDetails> findOrderDetailsByBrokerId(String brokerId);

    @Query( value = "select distinct o.id,o.issuer_name,o.user_name,o.customer_org_name,q.terminal,q.issue_price,o.create_time from orders o,quote_details q " +
            "where o.broker_id = ? and o.quote_detail_id = q.id ORDER BY ?#{#pageable}}",
            countQuery = "SELECT COUNT(1) FROM from orders o,quote_details q where o.broker_id = ? and o.quote_detail_id = q.id",
            nativeQuery = true)
    Page<OrderDetails> findOrderDetailsByBrokerIdByLimit(String brokerId, Pageable pageable);

}
