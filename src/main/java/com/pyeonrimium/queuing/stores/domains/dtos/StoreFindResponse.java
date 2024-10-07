package com.pyeonrimium.queuing.stores.domains.dtos;

import java.time.LocalTime;

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
	private String name;
	private String address;
	private String description;
	private String phone;
	private LocalTime startTime;
	private LocalTime endTime;
	private String closedDay;
	
}
