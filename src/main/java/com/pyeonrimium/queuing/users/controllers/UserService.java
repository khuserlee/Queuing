package com.pyeonrimium.queuing.users.controllers;

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
	 * @param signupRequestModel 회원가입 양식
	 * @return 회원가입 성공 여부
	 */
	public int signupConfirm(SignupRequestModel signupRequestModel) {
		System.out.println("[UserService] signupConfirm()");
		
		boolean isMember = userDao.isUserMember(signupRequestModel.getId());
		
		if(!isMember) {
			int result = userDao.insertUserAccount(signupRequestModel);
			
			if(result > 0)
				return USER_SIGNUP_SUCCESS;
			else 
				return USER_SIGNUP_FAIL;	
		} else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}
	
	public LoginRequest logininConfirm(LoginRequest loginRequest) {
		System.out.println("[UserService] loginConfirm()");
		
		LoginRequest loginedRequest = userDao.selectUser(loginRequest);
		
		if (loginedRequest != null)
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		else
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		
		return loginedRequest;
		
	}
}
