package com.pyeonrimium.queuing.reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

	private int reservation_id;
	private int user_id;
	private int store_id;
	private String reservation_number;
	private String reservation_date;
	private int party_size;
	private String request;
	private String status;
	private String create_at;
	private String modified_at;
	
}
