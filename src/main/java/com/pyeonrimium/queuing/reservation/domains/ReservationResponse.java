package com.pyeonrimium.queuing.reservation.domains;

import java.time.LocalDate;
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
public class ReservationResponse {
	private boolean isSuccess; //성공여부
	private String message; //메세지
	
	private String storeName; //식당이름
	private String reservationNumber; //예약 번호
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
	
	private int partySize; //인원 수
	private String request; // 요청 사항
}