package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.Issuers;
import com.sumscope.qpwb.ncd.data.model.db.StateOwnBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateOwnBankRepo extends JpaRepository<StateOwnBank, String> {}
