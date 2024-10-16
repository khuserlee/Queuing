package com.pyeonrimium.queuing.menus.domains.dtos;

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
public class MenuUpdateFormResponse {

	private boolean isSuccess;
	private String message;
	
	private long menuId;
	private long storeId;
	
	private String name;
	private int price;
	private String description;
}
