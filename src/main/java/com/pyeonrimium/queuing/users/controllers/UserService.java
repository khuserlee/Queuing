package com.pyeonrimium.queuing.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.users.domains.dtos.LoginResponse;
import com.pyeonrimium.queuing.users.domains.dtos.SignupResponse;
import com.pyeonrimium.queuing.users.domains.entities.UserEntity;

@Service
public class UserService {
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_SIGNUP_SUCCESS = 1;
	final static public int USER_SIGNUP_FAIL = -1;
	
	public static final String ROLE_USER = "USER";
	public static final String ROLE_MANAGER= "MANAGER";
	
	@Autowired
	UserDao userDao;
	
	/**
	 * 회원가입
	 * @param signupRequest 회원가입 양식
	 * @return 회원가입 결과
	 */
	public SignupResponse signup(SignupRequest signupRequest) {
		System.out.println("[UserService] signup()");
		
		boolean isRegistered = userDao.isUserMember(signupRequest.getId());
		
		if (isRegistered) {
			return SignupResponse.builder()
					.isSuccess(false)
					.message("회원가입에 실패했습니다.")
					.build();
		}
		
		int result = userDao.insertUserAccount(signupRequest);
		
		if (result <= 0) {
			return SignupResponse.builder()
					.isSuccess(false)
					.message("회원가입에 실패했습니다.")
					.build();
		}
		
		return SignupResponse.builder()
				.isSuccess(true)
				.message("회원가입을 완료했습니다!")
				.build();
	}
	
	/**
	 * 회원가입
	 * @param signupRequest 회원가입 양식
	 * @return 회원가입 성공 여부
	 */
	public int signupConfirm(SignupRequest signupRequest) {
		System.out.println("[UserService] signupConfirm()");
		
		boolean isMember = userDao.isUserMember(signupRequest.getId());
		
		if(!isMember) {
			int result = userDao.insertUserAccount(signupRequest);
			
			if(result > 0)
				return USER_SIGNUP_SUCCESS;
			else 
				return USER_SIGNUP_FAIL;	
		} else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}
	
	public LoginResponse login(LoginRequest loginRequest) {
		System.out.println("[UserService] login()");
		
		UserEntity userEntity = userDao.findUserById(loginRequest.getId());
		
		if (userEntity == null) {
			return LoginResponse.builder()
					.isSuccess(false)
					.message("아이디 또는 비밀번호를 확인해주세요.")
					.build();
		}
		
		// 비밀번호 비교
		if (!userEntity.getPassword().equals(loginRequest.getPassword())) {
			return LoginResponse.builder()
					.isSuccess(false)
					.message("아이디 또는 비밀번호를 확인해주세요.")
					.build();
		}
		
		// 유저가 점주인지 확인
		boolean isManager = userDao.checkUserIsManager(userEntity.getUserId());
		String role = isManager ? ROLE_MANAGER : ROLE_USER;
		
		return LoginResponse.builder()
				.isSuccess(true)
				.redirectUrl("/")
				.userId(userEntity.getUserId())
				.role(role)
				.build();
	}
	
	// 로그인 확인
	public LoginRequest loginConfirm(LoginRequest loginRequest) {
		System.out.println("[UserService] loginConfirm()");
		
		LoginRequest loginedRequest = userDao.selectUser(loginRequest);
		
		if (loginedRequest != null)
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		else
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		
		return loginedRequest;
		
	}
}
