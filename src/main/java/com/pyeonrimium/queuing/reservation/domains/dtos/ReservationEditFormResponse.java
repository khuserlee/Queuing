package com.pyeonrimium.queuing.reservation.domains.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEditFormResponse {

	private boolean isSuccess;
	private String message;
	
	private long storeId;
	private String storeName;
	
	private long reservationId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
	
	private int partySize;
	private String request;
	
}
