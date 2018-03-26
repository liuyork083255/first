package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.UserFilters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFiltersRepo extends JpaRepository<UserFilters,String> {
    UserFilters findByUserId(String userId);
}
