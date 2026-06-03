package com.invoice.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.invoice.api.entity.CartItem;

@Repository
public interface RepoCartItem extends JpaRepository<CartItem, Integer> {

    @Query("SELECT c FROM CartItem c WHERE c.userId = :userId")
    List<CartItem> findAllByUser_id(@Param("userId") Integer userId);

    @Query("SELECT c FROM CartItem c WHERE c.userId = :userId AND c.gtin = :gtin")
    Optional<CartItem> findByUser_idAndGtin(@Param("userId") Integer userId, @Param("gtin") String gtin);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.userId = :userId")
    void deleteAllByUser_id(@Param("userId") Integer userId);
}