package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.QuoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface QuoteDetailsRepo extends JpaRepository<QuoteDetails, String>{
    @Query(value="select * from quote_details where offer_id in ?1 and available = '1'", nativeQuery = true)
    List<QuoteDetails> findByOfferIdIn(List<String> offerIds);

    @Query(value="select * from quote_details where offer_id = ?1 and Date(modify_time) = ?2 order by modify_time", nativeQuery = true)
    List<QuoteDetails> findByOfferIdOrderByModifyTime(String offerId, Date date);

    List<QuoteDetails> findByOfferIdAndTerminalEquals(String offerId, String terminal);

    @Modifying
    @Transactional
    @Query(value="update quote_details set available = '0' where terminal = ?1 and offer_id = ?2 and issue_date = ?3", nativeQuery = true)
    void setAvailableByQuote(String terminal, String offerId, Date date);

    @Query(value="select max(id) FROM quote_details where offer_id = ?1 and TIME(modify_time) = ?2 and issue_price = ?3", nativeQuery = true)
    Long findIdByOfferIdAndQuoteTimeAndIssuePrice(String offerId, String quoteTime, BigDecimal price);

    @Query(value="SELECT id FROM orders WHERE user_id=?1 AND broker_id=?2 AND issuer_id=?3 AND quote_detail_id IN (\n" +
            "SELECT id FROM quote_details WHERE offer_id = ?4 )", nativeQuery = true)
    List<Long> findIdsByOfferId(String userId,String brokerId,String issuerId,String offerId);

    @Modifying
    @javax.transaction.Transactional
    @Query(value = "TRUNCATE TABLE quote_details",nativeQuery = true)
    void truncateTable();
}
