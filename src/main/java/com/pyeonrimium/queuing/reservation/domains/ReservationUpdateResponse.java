package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDateTime;

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
public class ReservationUpdateResponse {

	private boolean isSuccess;
	private String message;

	private long storeName;
	private String reservationNumber;
	private LocalDateTime reservationDate;
	private int partySize;
	private String request;

}
