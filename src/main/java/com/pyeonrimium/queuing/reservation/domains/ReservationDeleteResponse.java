package com.pyeonrimium.queuing.reservation.domains;

import org.springframework.http.HttpStatus;

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
public class ReservationDeleteResponse {

	// 성공 여부(성공: 200, 실패: 나머지)
	private HttpStatus httpStatus;
	
	// 클라이언트에게 전달할 메시지
	private String message;
	
	// 리다이렉트 url
	private String redirectUrl;
}
