package com.example.staticmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "static_analysis_result_tb")
public class StaticAnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "static_analysis_result_id", unique = true, nullable = false)
    Integer staticAnalysisResultId;
    @ManyToOne
    @JoinColumn(name="project_key")
    Project projectKey;
    @Column(name = "loc", nullable = false)
    Integer loc;
    @Column(name = "bugs", nullable = false)
    Integer bugs;
    @Column(name = "code_smells", nullable = false)
    Integer codeSmells;
    @Column(name = "vulnerabilities", nullable = false)
    Integer vulnerabilities;
    @Column(name = "complexity", nullable = false)
    Integer complexity;
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

}
