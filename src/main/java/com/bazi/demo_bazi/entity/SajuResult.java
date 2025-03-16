package com.bazi.demo_bazi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "saju_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SajuResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    @Column(name = "birth_time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime birthTime;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "year_pillar")
    private String yearPillar;
    
    @Column(name = "month_pillar")
    private String monthPillar;
    
    @Column(name = "day_pillar")
    private String dayPillar;
    
    @Column(name = "time_pillar")
    private String timePillar;
    
    @Column(name = "solar_lunar", length = 10)
    private String solarLunar = "양력";  // 기본값 양력
    
    @Column(name = "intercalation", length = 10)
    private String intercalation = "평달";  // 기본값 평달
    
    @Column(name = "birth_place", length = 100)
    private String birthPlace = "Unknown";
    
    @Column(name = "age")
    private String age = "00세";
    
    @Column(length = 500)
    private String majorFortunes;
    
    @Column(length = 1000)
    private String analysis;
    
    @Column(name = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    
    // 기본값 설정을 위한 메소드
    @PrePersist
    public void prePersist() {
        if (this.solarLunar == null) {
            this.solarLunar = "양력";
        }
        if (this.intercalation == null) {
            this.intercalation = "평달";
        }
        if (this.birthPlace == null) {
            this.birthPlace = "Unknown";
        }
        if (this.age == null) {
            this.age = "00세";
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
    }
}
