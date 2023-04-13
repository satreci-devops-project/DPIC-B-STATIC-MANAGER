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
@Table(name = "project_tb")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_key", unique = true, nullable = false)
    Integer projectKey;
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


}
