package com.pyeonrimium.queuing.stores.domains.entities;


import java.math.BigDecimal;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class StoreEntity {

	private Long storeId;
	private Long userId;
	
	private String name;
	private String address;
	private String description;
	private String phone;

	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime startTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime endTime;
	
	private String closedDay;
	
	private BigDecimal longitude;
	private BigDecimal latitude;
}

