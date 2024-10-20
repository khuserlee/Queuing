package com.pyeonrimium.queuing.stores.domains.dtos;

import java.time.LocalTime;
import java.util.List;

import com.pyeonrimium.queuing.menus.domains.entities.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreFindResponse {
	
	private boolean isSuccess;
	private String message;
	
	private long storeId;
	private long userId;
	
	private String name;
	private String address;
	private String roadAddress;
	private String detailAddress;
	private String description;
	private String phone;
	
	private LocalTime startTime;
	private LocalTime endTime;
	
	private String closedDay;
	
	private List<Menu> menus;
	
}
