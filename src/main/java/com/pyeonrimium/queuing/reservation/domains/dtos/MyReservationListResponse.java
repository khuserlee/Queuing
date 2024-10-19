package com.pyeonrimium.queuing.reservation.domains.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;

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
public class MyReservationListResponse {

	private HttpStatus httpStatus;
	private String message;
	private String redirectUrl;
	
	private int startPageNo;
	private int endPageNo;
	private int currentPageNo;
	private int lastPageNo;
	
	private List<MyReservation> reservations;
}
