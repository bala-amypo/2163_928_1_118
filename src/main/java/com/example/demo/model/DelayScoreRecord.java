package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delay_score_records", uniqueConstraints = @UniqueConstraint(columnNames = "poId"))
public class DelayScoreRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private Long poId;
    private Integer delayDays;
    private String delaySeverity;
    private Double score;
    private LocalDateTime computedAt;

    @PrePersist
    public void onCreate() {
        this.computedAt = LocalDateTime.now();
    }
}
