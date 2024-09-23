package com.pyeonrimium.queuing.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//예약화면 홈
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	
	private String nextPage;

	@GetMapping("/reservations/form") // 예약 신청 화면 불러오기
	public String reservationHome() {
		System.out.println("[ReservationController] reservationHome()");
		return "reservation/reservation_home";
	}
	
	// TODO: 예약 신청(C)

	@PostMapping("/reservation/ReservationConfirm")
	public String createReservation(ReservationVo reservationVo, Model model) {
		
		ReservationResponse result = reservationService.createReservation(reservationVo);
		
		if(result == null) {
			// 실패했을 때 경로
			nextPage = "/reservation/reservation_fail";
		} else {
			nextPage = "/reservation/reservation_success";
			model.addAttribute("result", result);
		}
		

		return nextPage;
	}
}
//		// TODO: 유저 정보 확인
//		if (session == null) {
//			return nextPage; // 실패했을 때 경로
//		}

		// TODO: 예약 정보 저장
		// service ~

		// TODO: 결과 반환
//		ReservationResponse result = service.createReservation(reservationDTO);
//
//		if (result == null) {
//			return nextPage;
//		}
//
//		nextPage = "/reservation/reservation_success"; // 성공했을 때 경로
//		return nextPage;
//	}

	// TODO: 예약 조회(R)
//	@GetMapping("/reservations?pageNo={pageNo}")
//	public String lookUpReservation(HttpSession session) {
//		String nextPage = ""; // 실패했을 때 경로
//
//		// TODO : 유저 예약 정보 조회
//		if (session == null) {
//			return nextPage; // 실패했을 때 경로
//		} else {
//
//		}
//
//		return nextPage;
//	}
	// TODO: 예약 수정(U)

	// TODO: 예약 삭제(D)

