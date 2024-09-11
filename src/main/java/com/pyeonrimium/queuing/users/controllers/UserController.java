package com.pyeonrimium.queuing.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	// 회원가입 페이지 매핑
	@GetMapping("/signup")
	public String signupForm() {
		System.out.println("[UserController] signupForm() called");
		String nextPage = "user/auth/signup";
		return nextPage;
	}
	
	// 회원가입 확인
	@PostMapping("/signupConfirm")
	public String signupConfirm(SignupRequestDTO signupRequestDTO) {
		System.out.println("[UserController] signupConfirm()");
		String nextPage = "user/auth/signup_ok";
		
		int result = userService.signupConfirm(signupRequestDTO);
		if(result <= 0)
		nextPage = "user/auth/signup_ng";
		return nextPage;
	}
	
	// 로그인
	@GetMapping("/login")
	public String loginForm() {
		System.out.println("[UserController] loginForm()");
		String nextPage = "/user/auth/login";
		return nextPage;
	}
	
	// 로그인 확인
	
	// 회원정보 찾기
	@GetMapping("/find_userInfo")
	public String findUserInfoForm() {
		System.out.println("[UserController] findUserInfoForm");
		String nextPage = "/user/auth/find_userInfo";
		return nextPage;
	}
	
	// 아이디 찾기 처리
	
	// 비밀번호 찾기 처리
	
	// 회원정보 수정
	
	// 회원정보 수정 확인
	
	// 로그아웃 확인

}
