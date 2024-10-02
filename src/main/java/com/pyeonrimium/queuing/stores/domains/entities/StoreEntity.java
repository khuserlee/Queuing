package com.pyeonrimium.queuing.stores.domains.entities;


import java.time.LocalTime;


import org.springframework.format.annotation.DateTimeFormat;

public class StoreEntity {
	private String name; 
	private String description;
	private String address;
	private String phone;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	private String closedDay;
	//name address description phone start_time end_time closed_day
}
