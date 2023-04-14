package com.example.staticmanager.repository;

import com.example.staticmanager.entity.StaticAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaticAnalysisResultRepository extends JpaRepository<StaticAnalysisResult, Integer> {
}
