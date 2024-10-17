package com.pyeonrimium.queuing.users.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Find_passwordRequest {
	
	private String id;
	private String name;
	private String phone;
}
