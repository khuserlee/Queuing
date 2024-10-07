package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdateRequest {

	private Long userId;
	private String reservationNumber;
	private String reservationId;
	private Long storeId;
	private String storeName;
	private int partySize;
	private String request;
	private String status;
	private String modifiedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
}
