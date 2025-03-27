package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Achievement;

@Repository

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
