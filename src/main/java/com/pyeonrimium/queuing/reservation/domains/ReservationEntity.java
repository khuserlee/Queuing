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
public class ReservationEntity {
	private int userId;
	private int storeId;
	private String reservationNumber;
	private LocalDateTime reservationDate;
	private int partySize;
	private String requset;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
}
