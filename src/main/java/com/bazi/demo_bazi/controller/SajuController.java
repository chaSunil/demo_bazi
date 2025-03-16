package com.bazi.demo_bazi.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import com.bazi.demo_bazi.dto.SajuRequestDto;
import com.bazi.demo_bazi.dto.SajuResponseDto;
import com.bazi.demo_bazi.service.SajuService;

@Slf4j
@RestController
@RequestMapping("/api/saju")
@RequiredArgsConstructor
public class SajuController {

    private final SajuService sajuService;

    @PostMapping("/calculate")
    public ResponseEntity<SajuResponseDto> calculateSaju(@RequestBody SajuRequestDto request) {
        log.info("사주 계산 요청 받음: {}", request);
        log.info("요청 상세 - 이름: {}, 생년월일: {}, 시간: {}, 성별: {}", 
            request.getName(), request.getBirthDate(), request.getBirthTime(), request.getGender());
        
        // 서비스 객체 확인
        log.info("SajuService 객체: {}", sajuService != null ? "정상" : "null");
        
        try {
            // 서비스 호출 전 로그
            log.info("SajuService.calculateSaju 호출 시작");
            SajuResponseDto result = sajuService.calculateSaju(request);
            log.info("SajuService.calculateSaju 호출 완료");
            
            // 응답 객체 검증
            log.info("응답 객체 필드 확인:");
            log.info("이름: {}", result.getName());
            log.info("년주: {}, 년간: {}, 년지: {}", 
                result.getYearPillar(), result.getYearStem(), result.getYearBranch());
            log.info("월주: {}, 월간: {}, 월지: {}", 
                result.getMonthPillar(), result.getMonthStem(), result.getMonthBranch());
            log.info("일주: {}, 일간: {}, 일지: {}", 
                result.getDayPillar(), result.getDayStem(), result.getDayBranch());
            log.info("시주: {}, 시간: {}, 시지: {}", 
                result.getTimePillar(), result.getTimeStem(), result.getTimeBranch());
            
            // DB 저장 부분
            try {
                log.info("SajuService.saveSajuResult 호출 시작");
                Long savedId = sajuService.saveSajuResult(result, request);
                log.info("사주 결과 저장 완료: ID={}", savedId);
            } catch (Exception e) {
                log.error("사주 결과 저장 중 오류 발생", e);
            }
            
            log.info("사주 계산 결과: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("사주 계산 중 오류 발생", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
