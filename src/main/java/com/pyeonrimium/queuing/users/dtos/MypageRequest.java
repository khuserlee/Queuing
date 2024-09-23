package com.pyeonrimium.queuing.users.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
	
public class MypageRequest {
	
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
}

