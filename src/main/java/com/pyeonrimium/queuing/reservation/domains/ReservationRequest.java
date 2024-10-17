package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
	
	
	private Long storeId;
	private String storeName;
	private int partySize;
	private String request;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
	
}
