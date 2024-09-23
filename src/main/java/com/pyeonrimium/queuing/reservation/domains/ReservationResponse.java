package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationResponse {
	private boolean isSuccess; //성공여부
	private String message; //메세지
	
	private String storeName; //식당이름
	private String reservationNumber; //예약 번호
	private LocalDateTime reservationDate; // 예약 날짜
	private int partySize; //인원 수
	private String request; // 요청 사항
}