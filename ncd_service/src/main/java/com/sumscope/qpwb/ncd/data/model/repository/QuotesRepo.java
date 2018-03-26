package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.Quotes;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface QuotesRepo extends JpaRepository<Quotes, Long> {
    @Query(value = "select * from quotes where broker_id = ?1 and outdated = 0 order by first_level_order asc" +
            ",second_level_order asc", nativeQuery = true)
    List<Quotes> findByBrokerIdAndIssuerDateOrderByFirstLevelOrderAscSecondLevelOrderAsc(String brokerId);

    @Query(value = "select * from quotes where issuer_code = ?1 and issuer_date = ?2 and rate = ?3 and outdated = 0 ",
            nativeQuery = true)
    List<Quotes> findByIssuerCodeAndIssuerDateAndRateEquals(String issuerId, Date date, String rate);

    @Modifying
    @Transactional
    @Query(value = "update quotes set outdated = 1 where outdated = 0", nativeQuery = true)
    void setOutdated();

    @Modifying
    @javax.transaction.Transactional
    @Query(value = "TRUNCATE TABLE quotes", nativeQuery = true)
    void truncateTable();
}
