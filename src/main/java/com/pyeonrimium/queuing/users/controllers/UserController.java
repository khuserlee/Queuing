package com.pyeonrimium.queuing.users.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.pyeonrimium.queuing.users.domains.dtos.Find_idRequest;
import com.pyeonrimium.queuing.users.domains.dtos.LoginResponse;
import com.pyeonrimium.queuing.users.domains.dtos.ProfileUpdateRequest;
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
	
	/**
	 * 아이디, 비밀번호 찾기 화면
	 * @return 회원정보 찾기 화면 표시
	 */
	@GetMapping("/users/find/form")
	public String findForm() {
		return "/user/auth/find_userInfo";
	}
	
	/**
	 * 아이디 찾기
	 * @param find_idRequest
	 * @param model
	 * @return
	 */
	@PostMapping("/users/find/id")
	public String find_idConfirm(Find_idRequest find_idRequest, Model model) {

		String foundId = userService.findIdConfirm(find_idRequest);
		
		if(foundId == null) {
			return "user/auth/find_id_ng";
		}
		
		model.addAttribute("foundId", foundId);
		
		return "user/auth/find_id_ok";
	}

	
	/**
	 * 비밀번호 찾기
	 * @param find_passwordRequest
	 * @param model
	 * @return
	 */
	@PostMapping("/users/find/password")
	public String find_passwordConfirm(Find_passwordRequest find_passwordRequest, Model model) {

		String newPassword = userService.findPasswordConfirm(find_passwordRequest);
		
		if (newPassword == null) {
			return "user/auth/find_password_ng";
		}
		
		model.addAttribute("newPassword", newPassword);
		
		return "user/auth/find_password_ok";
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return 홈 화면
	 */
	@PostMapping("/logout")
	public String logoutConfirm(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		
		return "redirect:/";
	}
	
	/**
	 * 프로필(회원정보) 화면
	 * @param session
	 * @return 로그인 세션 확인 시 마이페이지 화면 표시
	 */
	@GetMapping("/users/profile")
	public String profileform(HttpSession session, Model model) {
		
		System.out.println("[UserController] profileForm()");
		
		Long userId = (Long) session.getAttribute("user_id");
		if(userId == null) {
			return "redirect:/login/form";
		}
		
		ProfileUpdateRequest profileRequest = userService.getProfileUpdateRequest(userId);
	    model.addAttribute("profileRequest", profileRequest);
		
		return "user/auth/profile";
	}
	
	/**
	 * 프로필(회원정보) 수정
	 * @param profileUpdateRequest
	 * @param session
	 * @return
	 */
	@PatchMapping("/users/profile")
	public String updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest, HttpSession session) {
	    System.out.println("[UserController] updateProfile()");

	    String nextPage = "user/auth/updateProfile_ok";

	    int result = userService.updateProfileConfirm(profileUpdateRequest);
	    
	    if (result > 0) {
	        // 업데이트 후 세션을 새롭게 설정
	        ProfileUpdateRequest updatedProfile = userService.getProfileUpdateRequest(profileUpdateRequest.getUserId());
	        session.setAttribute("loginedProfileUpdateRequest", updatedProfile);
	        session.setMaxInactiveInterval(60 * 30);  // 세션 시간 설정
	    } else {
	        nextPage = "user/auth/updateProfile_ng";
	    }
	    return nextPage;
	}
}