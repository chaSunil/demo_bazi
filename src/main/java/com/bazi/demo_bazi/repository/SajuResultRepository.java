package com.bazi.demo_bazi.repository;

import com.bazi.demo_bazi.entity.SajuResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SajuResultRepository extends JpaRepository<SajuResult, Long> {
    List<SajuResult> findByName(String name);
}