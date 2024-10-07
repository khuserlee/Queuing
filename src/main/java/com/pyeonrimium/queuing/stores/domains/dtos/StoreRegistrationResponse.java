package com.pyeonrimium.queuing.stores.domains.dtos;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

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
public class StoreRegistrationResponse {
	private boolean isSuccess;
	private String message;
	private String redirectUrl;
	
	private Long storeId;
	private String name;
	private String description;
	private String address;
	private String phone;
	
	 @DateTimeFormat(pattern = "HH:mm")
	 private LocalTime startTime;
	 
	 @DateTimeFormat(pattern = "HH:mm")
	 private LocalTime endTime;
	 private String closedDay;
}
