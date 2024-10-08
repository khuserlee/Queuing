package com.pyeonrimium.queuing.users.controllers;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.users.domains.dtos.LoginResponse;
import com.pyeonrimium.queuing.users.domains.dtos.SignupResponse;
import com.pyeonrimium.queuing.users.domains.entities.UserEntity;
import com.pyeonrimium.queuing.users.domains.dtos.Find_idRequest;

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
		
		return loginedRequest;
	}
	
	// 아이디 찾기 확인
	public String findIdConfirm(Find_idRequest find_idRequest) {
		System.out.println("[UserService] findIdConfirm()");
		
		Find_idRequest selectedFind_idRequest = userDao.selectUser(find_idRequest.getName(), find_idRequest.getPhone());
		
		// 로그로 사용자 정보 확인
		System.out.println("Seclected User: " + selectedFind_idRequest);
		
		if(selectedFind_idRequest != null) {
			return selectedFind_idRequest.getId();
		}
		return null;
	}

	
	// 비밀번호 찾기 확인
	public String findPasswordConfirm(Find_passwordRequest find_passwordRequest) {
		System.out.println("[UserService] findPasswordConfirm()");
		
		Find_passwordRequest selectedFind_passwordRequest = userDao.selectUser(find_passwordRequest.getName(), find_passwordRequest.getPhone(), find_passwordRequest.getId());
		
		// 로그로 사용자 정보 확인
		System.out.println("Seclected User: " + selectedFind_passwordRequest);
	
		if (selectedFind_passwordRequest != null) {
			String newPassword = createNewPassword();
			int result = userDao.updatePassword(find_passwordRequest.getId(), newPassword);
	
			if (result > 0)
				return newPassword;
		}
		return null;
	}
	
	// 임시 비밀번호 생성
	private String createNewPassword() {
		System.out.println("[AdminService] createNewPassword()");
		
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z'
				};
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
		
			if (index % 2 == 0) 
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		
		}
		
		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());
		
		return stringBuffer.toString();
		
	}
}
