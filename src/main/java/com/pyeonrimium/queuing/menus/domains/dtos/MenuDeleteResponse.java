package com.pyeonrimium.queuing.menus.domains.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDeleteResponse {

	private HttpStatus httpStatus;	// HTTP 응답 상태 코드
	private String message;
	
	private String redirectUrl;
}
