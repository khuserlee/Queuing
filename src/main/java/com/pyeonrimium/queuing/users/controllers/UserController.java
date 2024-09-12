package com.pyeonrimium.queuing.users.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 회원가입 화면
	 * @return 회원가입 화면 표시
	 */
	@GetMapping("/signup")
	public String signupForm() {
		System.out.println("[UserController] signupForm() called");
		String nextPage = "user/auth/signup";
		return nextPage;
	}
	
	/**
	 * 회원가입 확인
	 * @param signupRequestModel
	 * @return 회원가입 성공 여부
	 */
	@PostMapping("/user/auth/signupConfirm")
	public String signupConfirm(SignupRequestModel signupRequestModel) {
		System.out.println("[UserController] signupConfirm()");
		String nextPage = "user/auth/signup_ok";
		
		int result = userService.signupConfirm(signupRequestModel);
		if(result <= 0)
			nextPage = "user/auth/signup_ng";
		return nextPage;
	}
	
	/**
	 * 로그인 화면
	 * @return 로그인 화면 표시
	 */
	@GetMapping("/login")
	public String loginForm() {
		System.out.println("[UserController] loginForm()");
		String nextPage = "/user/auth/login";
		return nextPage;
	}
	
	/**
	 * 로그인 확인
	 * @param
	 * @return
	 */
	@PostMapping("/user/auth/loginConfirm")
	public String loginConfirm(LoginRequest loginRequest, HttpSession session) {
		System.out.println("[UserController] loginConfirm()");
		String nextPage = "user/member/login_ok";
		LoginRequest loginedRequest = userService.loginConfirm(loginRequest);
		
		if(loginedRequest == null) {
			nextPage = "user/member/login_ng";
		} else {
			session.setAttribute("loginedRequest", loginedRequest);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage
	}
	
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
