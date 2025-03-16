package com.bazi.demo_bazi.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SajuRequestDto {
    private String name;
    private LocalDate birthDate;
    private LocalTime birthTime;
    private String gender;
    private String solarLunar = "양력";  // 기본값
    private String intercalation = "평달";  // 기본값
    private String birthPlace = "Unknown";  // 기본값
}