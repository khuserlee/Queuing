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
public class ProfileUpdateResponse {
	
	private boolean isSuccess;
	private String message;
	
	private long userId;
	private String id;
	private String name;
	private String address;
	private String phone;
}
