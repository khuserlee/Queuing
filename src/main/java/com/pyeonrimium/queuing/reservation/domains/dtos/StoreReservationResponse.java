package com.pyeonrimium.queuing.reservation.domains.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReservationResponse {
	
	private boolean isSuccess;
	private String message;
	
	private String storeName;
	List<StoreReservation> reservations;
	
	// 페이지네이션을 위한 변수
	private int startPageNo;
	private int endPageNo;
	private int currentPageNo;
	private int lastPageNo;
}
