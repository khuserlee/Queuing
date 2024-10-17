package com.pyeonrimium.queuing.users.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {
	
	private String id;
	private String password;
}
