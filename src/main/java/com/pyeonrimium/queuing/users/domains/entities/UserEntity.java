package com.pyeonrimium.queuing.users.domains.entities;

import java.time.LocalDateTime;

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
public class UserEntity {

	private long userId;
	
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
	private LocalDateTime createdAt;
	
}
