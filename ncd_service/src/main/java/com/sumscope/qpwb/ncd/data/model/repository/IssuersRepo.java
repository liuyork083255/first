package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.Issuers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuersRepo extends JpaRepository<Issuers, String>{
    Issuers getIssuersByIdEquals(String id);
}
