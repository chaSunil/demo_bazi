package com.bazi.demo_bazi.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.bazi.demo_bazi.dto.SajuRequestDto;
import com.bazi.demo_bazi.dto.SajuResponseDto;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import com.bazi.demo_bazi.entity.SajuResult;
import com.bazi.demo_bazi.repository.SajuResultRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class SajuService {

    // 천간
    private static final String[] HEAVENLY_STEMS = {"갑", "을", "병", "정", "무", "기", "경", "신", "임", "계"};
    
    // 지지
    private static final String[] EARTHLY_BRANCHES = {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};

    // 천간 한자
    private static final Map<String, String> HEAVENLY_STEMS_HANJA;
    static {
        HEAVENLY_STEMS_HANJA = new HashMap<>();
        HEAVENLY_STEMS_HANJA.put("갑", "甲");
        HEAVENLY_STEMS_HANJA.put("을", "乙");
        HEAVENLY_STEMS_HANJA.put("병", "丙");
        HEAVENLY_STEMS_HANJA.put("정", "丁");
        HEAVENLY_STEMS_HANJA.put("무", "戊");
        HEAVENLY_STEMS_HANJA.put("기", "己");
        HEAVENLY_STEMS_HANJA.put("경", "庚");
        HEAVENLY_STEMS_HANJA.put("신", "辛");
        HEAVENLY_STEMS_HANJA.put("임", "壬");
        HEAVENLY_STEMS_HANJA.put("계", "癸");
    }
    
    // 지지 한자
    private static final Map<String, String> EARTHLY_BRANCHES_HANJA;
    static {
        EARTHLY_BRANCHES_HANJA = new HashMap<>();
        EARTHLY_BRANCHES_HANJA.put("자", "子");
        EARTHLY_BRANCHES_HANJA.put("축", "丑");
        EARTHLY_BRANCHES_HANJA.put("인", "寅");
        EARTHLY_BRANCHES_HANJA.put("묘", "卯");
        EARTHLY_BRANCHES_HANJA.put("진", "辰");
        EARTHLY_BRANCHES_HANJA.put("사", "巳");
        EARTHLY_BRANCHES_HANJA.put("오", "午");
        EARTHLY_BRANCHES_HANJA.put("미", "未");
        EARTHLY_BRANCHES_HANJA.put("신", "申");
        EARTHLY_BRANCHES_HANJA.put("유", "酉");
        EARTHLY_BRANCHES_HANJA.put("술", "戌");
        EARTHLY_BRANCHES_HANJA.put("해", "亥");
    }

    private final SajuResultRepository sajuResultRepository;

    public SajuResponseDto calculateSaju(SajuRequestDto request) {
        log.info("SajuService.calculateSaju 시작: {}", request);
        log.info("요청 상세 - 이름: {}, 생년월일: {}, 시간: {}, 성별: {}", 
            request.getName(), request.getBirthDate(), request.getBirthTime(), request.getGender());
        
        SajuResponseDto response = new SajuResponseDto();
        response.setName(request.getName());

        try {
            // 1. 음력 변환 (나중에 구현)
            LocalDate lunarDate = convertToLunar(request.getBirthDate());
            
            // 2. 사주 계산
            int year = lunarDate.getYear();
            int month = lunarDate.getMonthValue();
            int day = lunarDate.getDayOfMonth();
            int hour = request.getBirthTime().getHour();
            
            log.info("계산 정보: 년={}, 월={}, 일={}, 시={}", year, month, day, hour);

            // 년주 계산 (60갑자)
            int sexagenaryCycle = year % 60;
            int yearStem = (sexagenaryCycle - 4) % 10;  // 1984가 갑자년
            if (yearStem < 0) yearStem += 10;
            int yearBranch = (sexagenaryCycle - 4) % 12;
            if (yearBranch < 0) yearBranch += 12;
            
            String yearStemStr = HEAVENLY_STEMS[yearStem];
            String yearBranchStr = EARTHLY_BRANCHES[yearBranch];
            
            // 년주 설정
            response.setYearPillar(yearStemStr + yearBranchStr);
            response.setYearStem(yearStemStr);
            response.setYearBranch(yearBranchStr);
            response.setYearStemHanja(HEAVENLY_STEMS_HANJA.get(yearStemStr));
            response.setYearBranchHanja(EARTHLY_BRANCHES_HANJA.get(yearBranchStr));
            
            // 년도 정보 설정
            response.setYear(String.valueOf(year));
            
            log.info("년주 계산 결과: 천간={}, 지지={}, 한자천간={}, 한자지지={}", 
                yearStemStr, yearBranchStr, 
                HEAVENLY_STEMS_HANJA.get(yearStemStr), 
                EARTHLY_BRANCHES_HANJA.get(yearBranchStr));

            // 월주 계산 - 완전히 수정
            int monthStem;
            switch (yearStem) {
                case 0: case 5: // 갑, 기
                    monthStem = (month + 1) % 10; // 병부터 시작
                    break;
                case 1: case 6: // 을, 경
                    monthStem = (month + 3) % 10; // 무부터 시작
                    break;
                case 2: case 7: // 병, 신
                    monthStem = (month + 5) % 10; // 경부터 시작
                    break;
                case 3: case 8: // 정, 임
                    monthStem = (month + 7) % 10; // 임부터 시작
                    break;
                case 4: case 9: // 무, 계
                    monthStem = (month + 9) % 10; // 갑부터 시작
                    break;
                default:
                    monthStem = 0;
            }
            if (monthStem < 0) monthStem += 10;
            
            // 월지 계산 - 간소화
            int monthBranch = (month + 1) % 12; // 1월은 인(寅)월(2)
            if (monthBranch == 0) monthBranch = 12;
            monthBranch = (monthBranch + 1) % 12; // 인(寅)월은 인덱스 2
            
            String monthStemStr = HEAVENLY_STEMS[monthStem];
            String monthBranchStr = EARTHLY_BRANCHES[monthBranch];
            
            // 월주 설정
            response.setMonthPillar(monthStemStr + monthBranchStr);
            response.setMonthStem(monthStemStr);
            response.setMonthBranch(monthBranchStr);
            response.setMonthStemHanja(HEAVENLY_STEMS_HANJA.get(monthStemStr));
            response.setMonthBranchHanja(EARTHLY_BRANCHES_HANJA.get(monthBranchStr));
            
            log.info("월주 계산 결과: 천간={}, 지지={}, 한자천간={}, 한자지지={}", 
                monthStemStr, monthBranchStr, 
                HEAVENLY_STEMS_HANJA.get(monthStemStr), 
                EARTHLY_BRANCHES_HANJA.get(monthBranchStr));

            // 일주 계산 - 정확한 기준일 사용
            // 1900년 1월 31일은 양력으로 경진일(庚辰日)로 알려져 있음
            LocalDate baseDate = LocalDate.of(1900, 1, 31);
            int baseStem = 6; // 경(庚)
            int baseBranch = 4; // 진(辰)

            int daysCycle = (int) ChronoUnit.DAYS.between(baseDate, request.getBirthDate());
            int dayStem = (daysCycle + baseStem) % 10;
            if (dayStem < 0) dayStem += 10;
            int dayBranch = (daysCycle + baseBranch) % 12;
            if (dayBranch < 0) dayBranch += 12;
            
            String dayStemStr = HEAVENLY_STEMS[dayStem];
            String dayBranchStr = EARTHLY_BRANCHES[dayBranch];
            
            // 일주 설정
            response.setDayPillar(dayStemStr + dayBranchStr);
            response.setDayStem(dayStemStr);
            response.setDayBranch(dayBranchStr);
            response.setDayStemHanja(HEAVENLY_STEMS_HANJA.get(dayStemStr));
            response.setDayBranchHanja(EARTHLY_BRANCHES_HANJA.get(dayBranchStr));
            
            log.info("일주 계산 결과: 천간={}, 지지={}, 한자천간={}, 한자지지={}", 
                dayStemStr, dayBranchStr, 
                HEAVENLY_STEMS_HANJA.get(dayStemStr), 
                EARTHLY_BRANCHES_HANJA.get(dayBranchStr));

            // 시주 계산 - 간소화
            int timeBaseIndex = (dayStem % 5) * 2;  // 일간에 따른 시간 천간의 시작값
            int adjustedHour = hour;
            if (hour >= 23 || hour < 1) {
                adjustedHour = 0;  // 자시(23-1시)
            }
            
            int timeIndex = (adjustedHour + 1) / 2;
            if (timeIndex == 0) timeIndex = 12;  // 자시는 12로 처리
            
            int timeStem = (timeBaseIndex + timeIndex - 1) % 10;
            if (timeStem < 0) timeStem += 10;
            
            int timeBranch = (timeIndex - 1) % 12;
            if (timeBranch < 0) timeBranch += 12;
            
            String timeStemStr = HEAVENLY_STEMS[timeStem];
            String timeBranchStr = EARTHLY_BRANCHES[timeBranch];
            
            // 시주 설정
            response.setTimePillar(timeStemStr + timeBranchStr);
            response.setTimeStem(timeStemStr);
            response.setTimeBranch(timeBranchStr);
            response.setTimeStemHanja(HEAVENLY_STEMS_HANJA.get(timeStemStr));
            response.setTimeBranchHanja(EARTHLY_BRANCHES_HANJA.get(timeBranchStr));
            
            log.info("시주 계산 결과: 천간={}, 지지={}, 한자천간={}, 한자지지={}", 
                timeStemStr, timeBranchStr, 
                HEAVENLY_STEMS_HANJA.get(timeStemStr), 
                EARTHLY_BRANCHES_HANJA.get(timeBranchStr));

            // 3. 대운수 계산
            calculateMajorFortunes(yearStem, monthStem, request.getGender().equals("M"), month, response);

            // 최종 응답 객체 로그 출력 - 모든 필드 확인
            log.info("최종 응답 객체: {}", response);
            log.info("응답 객체 필드 확인:");
            log.info("이름: {}", response.getName());
            log.info("년도: {}", response.getYear());
            log.info("년주: {}, 년간: {}, 년지: {}, 년간한자: {}, 년지한자: {}", 
                response.getYearPillar(), response.getYearStem(), response.getYearBranch(), 
                response.getYearStemHanja(), response.getYearBranchHanja());
            log.info("월주: {}, 월간: {}, 월지: {}, 월간한자: {}, 월지한자: {}", 
                response.getMonthPillar(), response.getMonthStem(), response.getMonthBranch(), 
                response.getMonthStemHanja(), response.getMonthBranchHanja());
            log.info("일주: {}, 일간: {}, 일지: {}, 일간한자: {}, 일지한자: {}", 
                response.getDayPillar(), response.getDayStem(), response.getDayBranch(), 
                response.getDayStemHanja(), response.getDayBranchHanja());
            log.info("시주: {}, 시간: {}, 시지: {}, 시간한자: {}, 시지한자: {}", 
                response.getTimePillar(), response.getTimeStem(), response.getTimeBranch(), 
                response.getTimeStemHanja(), response.getTimeBranchHanja());
            log.info("대운수: {}", response.getMajorFortunes());
            log.info("대운수한자: {}", response.getMajorFortuneChars());
            log.info("대운수나이: {}", response.getMajorFortuneAges());
            log.info("대운수방향: {}", response.getMajorFortuneDirection());
        } catch (Exception e) {
            log.error("사주 계산 중 오류 발생", e);
            log.error("오류 상세", e);  // 스택 트레이스 출력
            
            // 기본값 설정
            response.setYearStem("갑");
            response.setYearBranch("자");
            response.setYearStemHanja("甲");
            response.setYearBranchHanja("子");
            response.setMonthStem("갑");
            response.setMonthBranch("자");
            response.setMonthStemHanja("甲");
            response.setMonthBranchHanja("子");
            response.setDayStem("갑");
            response.setDayBranch("자");
            response.setDayStemHanja("甲");
            response.setDayBranchHanja("子");
            response.setTimeStem("갑");
            response.setTimeBranch("자");
            response.setTimeStemHanja("甲");
            response.setTimeBranchHanja("子");
            response.setYear("2023");
        }
        
        log.info("SajuService.calculateSaju 종료: {}", response);
        return response;
    }

    private void calculateMajorFortunes(int yearStem, int monthStem, boolean isMale, int birthMonth, SajuResponseDto response) {
        List<String> fortunes = new ArrayList<>();  // 천간
        List<String> fortuneBranches = new ArrayList<>();  // 지지
        List<String> chars = new ArrayList<>();  // 천간 한자
        List<String> branchChars = new ArrayList<>();  // 지지 한자
        List<Integer> ages = new ArrayList<>();
        
        // 대운 방향 결정: 양간(갑병무경임)이면서 남자 또는 음간(을정기신계)이면서 여자는 순행
        // 그 외에는 역행
        boolean isYangStem = yearStem % 2 == 0;  // 갑(0), 병(2), 무(4), 경(6), 임(8)은 양간
        int direction = (isYangStem && isMale) || (!isYangStem && !isMale) ? 1 : -1;
        
        // 대운 시작 나이 계산
        int startAge;
        if ((isYangStem && isMale) || (!isYangStem && !isMale)) {
            // 양간남자 또는 음간여자: (10 - 생월)세에 시작
            startAge = 10 - birthMonth;
        } else {
            // 음간남자 또는 양간여자: 생월세에 시작
            startAge = birthMonth;
        }
        // 최소 1세부터 시작하도록 조정
        if (startAge < 1) startAge = 1;
        
        log.info("대운 계산: 년간={}, 월간={}, 성별={}, 방향={}, 생월={}, 시작나이={}", 
                HEAVENLY_STEMS[yearStem], HEAVENLY_STEMS[monthStem], 
                isMale ? "남" : "여", direction > 0 ? "순행" : "역행", birthMonth, startAge);
        
        // 12개의 대운수 계산 (110세 이상까지)
        int fortuneCount = 12;  // 최소 12개 생성
        for (int i = 0; i < fortuneCount; i++) {
            // 천간 계산
            int stemIndex = (monthStem + (direction * i) + 10) % 10;
            String stem = HEAVENLY_STEMS[stemIndex];
            fortunes.add(stem);
            chars.add(HEAVENLY_STEMS_HANJA.get(stem));
            
            // 지지 계산 (월지에서 시작하여 순행/역행)
            int monthBranchIndex = (birthMonth + 1) % 12;
            if (monthBranchIndex == 0) monthBranchIndex = 12;
            monthBranchIndex = (monthBranchIndex - 1) % 12;
            
            int branchIndex = (monthBranchIndex + (direction * i) + 12) % 12;
            String branch = EARTHLY_BRANCHES[branchIndex];
            fortuneBranches.add(branch);
            branchChars.add(EARTHLY_BRANCHES_HANJA.get(branch));
            
            // 나이 계산
            ages.add(startAge + (i * 10));
        }
        
        // 대운수 설정
        response.setMajorFortunes(fortunes);
        response.setMajorFortuneChars(chars);
        response.setMajorFortuneBranches(fortuneBranches);  // 지지 추가
        response.setMajorFortuneBranchChars(branchChars);  // 지지 한자 추가
        response.setMajorFortuneAges(ages);
        response.setMajorFortuneDirection(direction > 0 ? "순행" : "역행");
        response.setMajorFortuneStartAge(startAge);
        
        log.info("대운수 계산 결과: 천간={}, 천간한자={}, 지지={}, 지지한자={}, 나이={}, 방향={}, 시작나이={}", 
            fortunes, chars, fortuneBranches, branchChars, ages, direction > 0 ? "순행" : "역행", startAge);
    }

    private LocalDate convertToLunar(LocalDate solarDate) {
        // TODO: 음력 변환 로직 구현
        // 임시로 양력을 그대로 반환
        return solarDate;
    }

    public Long saveSajuResult(SajuResponseDto response, SajuRequestDto request) {
        // 대운수 문자열로 변환
        StringBuilder fortunesStr = new StringBuilder();
        if (response.getMajorFortunes() != null) {
            for (int i = 0; i < response.getMajorFortunes().size(); i++) {
                fortunesStr.append(response.getMajorFortunes().get(i));
                if (i < response.getMajorFortunes().size() - 1) {
                    fortunesStr.append(",");
                }
            }
        }
        
        // 나이 계산
        int age = LocalDate.now().getYear() - request.getBirthDate().getYear() + 1;
        
        // 엔티티 생성 및 저장
        SajuResult result = SajuResult.builder()
            .name(request.getName())
            .birthDate(request.getBirthDate())
            .birthTime(request.getBirthTime())
            .gender(request.getGender())
            .yearPillar(response.getYearPillar())
            .monthPillar(response.getMonthPillar())
            .dayPillar(response.getDayPillar())
            .timePillar(response.getTimePillar())
            .majorFortunes(fortunesStr.toString())
            .solarLunar("양력")  // 기본값
            .intercalation("평달")  // 기본값
            .birthPlace("Unknown")  // 기본값
            .age(age + "세")
            .createdAt(LocalDate.now())
            .build();
        
        SajuResult saved = sajuResultRepository.save(result);
        return saved.getId();
    }
}