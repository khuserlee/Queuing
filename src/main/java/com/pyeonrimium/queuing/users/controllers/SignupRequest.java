package com.pyeonrimium.queuing.users.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
}
