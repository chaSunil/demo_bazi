package com.bazi.demo_bazi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.bazi.demo_bazi.repository.SajuResultRepository;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final SajuResultRepository sajuResultRepository;
    
    @GetMapping
    public String adminPage(Model model) {
        log.info("관리자 페이지 요청 받음");
        model.addAttribute("results", sajuResultRepository.findAll());
        log.info("데이터 조회 완료: {} 건", sajuResultRepository.count());
        return "admin";
    }
}