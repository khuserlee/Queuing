package com.pyeonrimium.queuing.users.controllers;

import java.security.SecureRandom;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_SIGNUP_SUCCESS = 1;
	final static public int USER_SIGNUP_FAIL = -1;
	
	@Autowired
	UserDao userDao;
	
	
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
	
	// 비밀번호 찾기 확인
	public int findPasswordConfirm(Find_passwordRequest find_passwordRequest) {
		System.out.println("[UserService] findPasswordConfirm()");
		
		Find_passwordRequest selectedFind_passwordRequest = userDao.selectUser(find_passwordRequest.getId(), find_passwordRequest.getName(), find_passwordRequest.getPhone());
		
		int result = 0;
		
		if (selectedFind_passwordRequest != null) {
			String newPassword = createNewPassword();
			result = userDao.updatePassword(find_passwordRequest.getId(), newPassword);
			/*
			if (result > 0)
				sendNewPasswordByMail(find_passwordRequest.getPhone(), newPassword);
		*/}
		return result;
	}
	
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
