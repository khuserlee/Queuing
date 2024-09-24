package com.pyeonrimium.queuing.users.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pyeonrimium.queuing.users.domains.dtos.Find_idRequest;

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
	 * @param signupRequest
	 * @return 회원가입 성공 시 : signup_ok
	 * 				회원가입 실패 시 : signup_ng
	 */
	@PostMapping("/user/auth/signupConfirm")
	public String signupConfirm(SignupRequest signupRequest) {
		System.out.println("[UserController] signupConfirm()");
		String nextPage = "user/auth/signup_ok";
		
		int result = userService.signupConfirm(signupRequest);
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
	 * @param loginRequest
	 * @param session
	 * @return 로그인 성공 시 : login_ok
	 * 로그인 실패시 : login_ng
	 */
	@PostMapping("/user/auth/loginConfirm")
	public String loginConfirm(LoginRequest loginRequest, HttpSession session) {
		System.out.println("[UserController] loginConfirm()");
		String nextPage = "user/auth/login_ok";
		
		LoginRequest loginedRequest = userService.loginConfirm(loginRequest);
		
		if(loginedRequest == null) {
			nextPage = "user/auth/login_ng";
		} else {
			session.setAttribute("loginedRequest", loginedRequest);
			session.setMaxInactiveInterval(60 * 10);
		}
		return nextPage;
	}
	
	/**
	 * 회원정보 찾기 화면
	 * @return 회원정보 찾기 화면 표시
	 */
	@GetMapping("/find_userInfo")
	public String findUserInfoForm() {
		System.out.println("[UserController] findUserInfoForm");
		String nextPage = "/user/auth/find_userInfo";
		return nextPage;
	}
	
	// 아이디 찾기 처리

	
	// 비밀번호 찾기 처리
	@PostMapping("/user/auth/find_passwordConfirm")
	public String find_passwordConfirm(Find_passwordRequest find_passwordRequest, Model model) {
		System.out.println("[UserController] find_passwordConfirm()");
		
		String newPassword = userService.findPasswordConfirm(find_passwordRequest);
		String nextPage = "user/auth/find_password_ok";
		
		if (newPassword == null) {
			nextPage = "user/auth/find_password_ng";
		} else {
			model.addAttribute("newPassword", newPassword);
		}
		return nextPage;
	}
	
	// 회원정보 수정
	
	// 회원정보 수정 확인
	
	/**
	 * 로그아웃 확인
	 * @param session
	 * @return 로그아웃 시 초기화면 반환 (아마 home?)
	 */
	@PostMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserController] logoutConfirm()");
		
		String nextPage = "redirect:/";
		
		session.invalidate();
		
		return nextPage;
	}
	
	/**
	 * 마이페이지 화면
	 * @param session
	 * @return 로그인 세션 확인 시 마이페이지 화면 표시
	 */
	@GetMapping("/mypage")
	public String mypageform(HttpSession session) {
		System.out.println("[UserController] mypageform() called");
		LoginRequest loginedRequest = (LoginRequest) session.getAttribute("loginedRequest");
		if(loginedRequest == null) {
			return "redirect:/login";
		}
		return "user/auth/mypage";
	}
}
