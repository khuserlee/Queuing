package com.pyeonrimium.queuing.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
//예약화면 홈
public class ReservationController {
	@GetMapping("/reservation")
	public String reservationHome() {
		System.out.println("[ReservationController] reservationHome");
		return "user/reservation_home";
	}
	@PostMapping("/reservation")
	public String reservationResult() {
		System.out.println("[Reservation Controller] reservation_success");
		return "user/reservation_success";
	};
}