package com.pyeonrimium.queuing.reservation.domains;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationListResponse {

	private List<ReservationEntity> list;

}