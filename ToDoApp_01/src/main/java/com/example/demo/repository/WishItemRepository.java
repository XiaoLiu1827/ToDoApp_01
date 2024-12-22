package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.WishItem;

@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long>{
	List<WishItem> findByUserId(Long userId);
}
