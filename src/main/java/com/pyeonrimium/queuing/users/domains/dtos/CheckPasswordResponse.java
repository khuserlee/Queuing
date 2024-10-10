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
public class CheckPasswordResponse {

	private boolean isSuccess;
	private String message;
	private String redirectUrl;
}
