package com.pyeonrimium.queuing.users.domains.dtos;

import org.springframework.http.HttpStatus;

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
public class SignupResponse {
	
	private HttpStatus httpStatus;
	private String message;
	private String redirectUrl;
	
}
