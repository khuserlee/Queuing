package com.pyeonrimium.queuing.users.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pyeonrimium.queuing.users.domains.dtos.LoginResponse;
import com.pyeonrimium.queuing.users.domains.dtos.SignupResponse;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 회원가입 화면
	 * @return 회원가입 화면 표시
	 */
	@GetMapping("/signup/form")
	public String signupForm() {
		System.out.println("[UserController] signupForm() called");

		return "user/auth/signup";
	}

	/**
	 * 회원가입 확인
	 * @param signupRequest 회원가입 양식
	 * @param model
	 * @return 회원가입 결과 화면
	 */
	@PostMapping("/signup")
	public String signup(SignupRequest signupRequest, Model model) {
		System.out.println("[UserController] signupConfirm()");

		SignupResponse signupResponse = userService.signup(signupRequest);
		model.addAttribute("signupResponse", signupResponse);

		return "user/auth/signup_result";
	}

	/**
	 * 로그인 화면
	 * @return 로그인 화면 표시
	 */
	@GetMapping("/login/form")
	public String loginForm() {
		System.out.println("[UserController] loginForm()");
		
		return "/user/auth/login";
	}

	/**
	 * 로그인 화면
	 * @param loginRequest 로그인 양식
	 * @param model
	 * @param session
	 * @return 로그인 결과 화면
	 */
	@PostMapping("/login")
	public String login(LoginRequest loginRequest, Model model, HttpSession session) {
		System.out.println("[UserController] loginConfirm()");
		
		LoginResponse loginResponse = userService.login(loginRequest);
		model.addAttribute("loginResponse", loginResponse);
		
		// 세션 정보 입력
		if (loginResponse.isSuccess()) {
			session.setAttribute("user_id", loginResponse.getUserId());
			session.setAttribute("role", loginResponse.getRole());
			
			// 유효기간 설정
			session.setMaxInactiveInterval(60 * 30);
		}
		
		return "user/auth/login_result";
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
