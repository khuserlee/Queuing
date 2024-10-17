package com.pyeonrimium.queuing.menus.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRegistrationRequest {

	private String name;
	private int price;
	private String description;
}
