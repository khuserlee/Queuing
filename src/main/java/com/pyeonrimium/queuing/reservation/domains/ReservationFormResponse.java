package com.pyeonrimium.queuing.reservation.domains;

import java.sql.Time;

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

public class ReservationFormResponse {
	private long storeId;
	private String Name;
	private Time startTime;
	private Time endTime;
	private String closedDay;
}
