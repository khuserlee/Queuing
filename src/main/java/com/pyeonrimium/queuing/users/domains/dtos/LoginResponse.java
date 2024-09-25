package com.pyeonrimium.queuing.users.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private boolean isSuccess;
	private String message;
	private String redirectUrl;
	
	private long userId;
	private String role;
	
}
