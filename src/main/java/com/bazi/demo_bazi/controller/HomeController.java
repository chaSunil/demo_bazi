package com.bazi.demo_bazi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        log.info("홈 컨트롤러 호출되었습니다.");
        return "main";
    }
}