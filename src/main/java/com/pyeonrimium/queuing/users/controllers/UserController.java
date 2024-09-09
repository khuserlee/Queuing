package com.pyeonrimium.queuing.users.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 로그인 페이지 매핑
    @GetMapping("/user")
    public String signup() {
        System.out.println("[UserController] signup()");
        return "user/auth/signup";  // login.jsp로 이동
    }

    // ID/PW 찾기 페이지 매핑
    @GetMapping("/user/auth/find_userInfo")
    public String findUserInfo() {
        System.out.println("[UserController] findUserInfo()");
        return "user/auth/find_userInfo";  // find_userInfo.jsp로 이동
    }
}