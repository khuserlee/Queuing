package com.pyeonrimium.queuing.stores.domains.dtos;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
//등록위한 클래스
@Getter
@Setter
public class StoreRegisterationRequest {
	private String name;
	private String description;
	private String address;
	private String phone;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	private String closedDay;
	
	private BigDecimal longitude;
	private BigDecimal latitude;
}
