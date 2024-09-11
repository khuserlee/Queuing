package com.pyeonrimium.queuing.users.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {
	
	private int user_id;
	// 회원고유번호도 필요하지 않은지?
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
	/*	String u_m_reg_date;
	String u_m_mod_date;*/
}
