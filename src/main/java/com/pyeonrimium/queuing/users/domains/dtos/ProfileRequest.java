package com.pyeonrimium.queuing.users.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProfileRequest {
	
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
}
