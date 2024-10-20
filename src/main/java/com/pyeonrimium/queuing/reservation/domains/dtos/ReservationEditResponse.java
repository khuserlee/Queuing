package com.pyeonrimium.queuing.reservation.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEditResponse {

	private boolean isSuccess;
	private String message;
	private String redirectUrl;
}
