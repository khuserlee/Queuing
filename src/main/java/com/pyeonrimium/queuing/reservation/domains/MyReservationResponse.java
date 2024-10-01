package com.pyeonrimium.queuing.reservation.domains;

import java.util.List;

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

public class MyReservationResponse {

	private boolean isSuccess;
	private String message;

	
	private List<ReservationEntity> reservations;

}
