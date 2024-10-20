package com.pyeonrimium.queuing.reservation.domains.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationEditRequest {

	private long reservationId;
	private int partySize;
	private String request;
	
}
