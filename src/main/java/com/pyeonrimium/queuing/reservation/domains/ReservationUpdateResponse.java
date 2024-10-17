package com.pyeonrimium.queuing.reservation.domains;

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
	private String redirectUrl;

}
