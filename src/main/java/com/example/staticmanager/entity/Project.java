package com.example.staticmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "project_tb")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id", unique = true)
    Integer projectId;
    @Column(name = "project_key", unique = true, nullable = false)
    String projectKey;
    @Column(name = "name", nullable = false)
    String name;
    @Column(name = "qualifier", nullable = false)
    String qualifier;
    @Column(name = "visibility", nullable = false)
    String visibility;
    @Column(name = "last_analysis_date", nullable = false)
    LocalDateTime lastAnalysisDate;
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;


    public Project(String projectKey, String name, String qualifier, String visibility, LocalDateTime lastAnalysisDate, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.projectKey = projectKey;
        this.name = name;
        this.qualifier = qualifier;
        this.visibility = visibility;
        this.lastAnalysisDate = lastAnalysisDate;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }
}
