package com.sumscope.qpwb.ncd.data.model.repository;

import com.sumscope.qpwb.ncd.data.model.db.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavouritesRepo extends JpaRepository<Favourites,Long> {
    @Modifying
    @Transactional
    void deleteByUserIdAndIssuerId(String userId, String issuerId);

    Favourites findByUserIdAndIssuerId(String userId, String issuerId);

    List<Favourites> findByUserId(String userId);
}
