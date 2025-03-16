package com.bazi.demo_bazi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@ToString
public class SajuResponseDto {
    private String name = "";
    
    // 년주
    private String yearPillar = "";
    private String yearStem = "";
    private String yearBranch = "";
    private String yearStemHanja = "";
    private String yearBranchHanja = "";
    
    // 월주
    private String monthPillar = "";
    private String monthStem = "";
    private String monthBranch = "";
    private String monthStemHanja = "";
    private String monthBranchHanja = "";
    
    // 일주
    private String dayPillar = "";
    private String dayStem = "";
    private String dayBranch = "";
    private String dayStemHanja = "";
    private String dayBranchHanja = "";
    
    // 시주
    private String timePillar = "";
    private String timeStem = "";
    private String timeBranch = "";
    private String timeStemHanja = "";
    private String timeBranchHanja = "";
    
    // 대운수
    private List<String> majorFortunes = new ArrayList<>();
    private List<String> majorFortuneChars = new ArrayList<>();
    private List<String> majorFortuneBranches = new ArrayList<>();
    private List<String> majorFortuneBranchChars = new ArrayList<>();
    private List<Integer> majorFortuneAges = new ArrayList<>();
    private String majorFortuneDirection = "";
    private Integer majorFortuneStartAge = 0;

    private String year = "";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getMajorFortuneStartAge() {
        return majorFortuneStartAge;
    }

    public void setMajorFortuneStartAge(Integer majorFortuneStartAge) {
        this.majorFortuneStartAge = majorFortuneStartAge;
    }

    public List<String> getMajorFortuneBranches() {
        return majorFortuneBranches;
    }

    public void setMajorFortuneBranches(List<String> majorFortuneBranches) {
        this.majorFortuneBranches = majorFortuneBranches;
    }

    public List<String> getMajorFortuneBranchChars() {
        return majorFortuneBranchChars;
    }

    public void setMajorFortuneBranchChars(List<String> majorFortuneBranchChars) {
        this.majorFortuneBranchChars = majorFortuneBranchChars;
    }
}