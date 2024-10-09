package com.pyeonrimium.queuing.stores.domains.dtos;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreEditRequest {
	private String name;
	private String description;
	private String address;
	private String detailAddress;
	private String phone;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	private String closedDay;
	
	private BigDecimal longitude;
	private BigDecimal latitude;
}
