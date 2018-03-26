package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserContactRepo extends JpaRepository<UserContact,String>{
    UserContact findByUserId(String userId);
}
