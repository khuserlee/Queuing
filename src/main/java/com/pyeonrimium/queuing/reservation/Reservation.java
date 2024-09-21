package com.pyeonrimium.queuing.reservation;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {
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
