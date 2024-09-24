package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class ReservationEntity {
	private long userId;
	private long storeId;
	private String reservationNumber;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
	
	private int partySize;
	private String request;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
