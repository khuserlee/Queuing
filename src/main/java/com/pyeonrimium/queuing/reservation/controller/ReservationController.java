package com.pyeonrimium.queuing.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pyeonrimium.queuing.reservation.Service.ReservationService;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;

@Controller
//예약화면 홈
public class ReservationController {
	@Autowired
	ReservationService reservationService;
	
	private String nextPage;
	
	/**
	 * 예약 신청 화면 불러오기
	 * @param storeId 가게 고유 ID
	 * @param model
	 * @return 성공: 예약 페이지, 실패: 실패 안내 페이지
	 */
	@GetMapping("/reservations/form/{storeId}")
	public String reservationHome(@PathVariable long storeId, Model model) {
		System.out.println("[ReservationController] reservationHome()");
		
		// storeId를 통해서 storeName 가져오기
		String storeName = reservationService.getStoreName(storeId);
		
		if (storeName == null) {
			// TODO: 조회 실패 처리
			System.out.println("[ReservationController] 가게 이름 조회 실패");
		}
		
		// model에 가게 이름 저장
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeName", storeName);
		
		return "reservation/reservation_home";
	    
//	    예약 화면에 필요한 정보 불러오기 => 식당 정보
//	    System.out.println("[ReservationController] getReservationForm");
//	    model.addAttribute("reservationFormResponse", response); // 올바른 변수명 사용

	}
	
	

	private Long getUserId(HttpSession session) {
//		user_id 조회
//		Long userId = (Long)session.getAttribute("user_id");
//		return userId;
//		
		Long userId = new Long(1);
		return userId;
	}
//		
	// TODO: 예약 신청(C)

	@PostMapping("/reservations")
	public String createReservation(ReservationRequest reservationRequest, Model model,HttpSession session) {
		Long userId = getUserId(session);
		
		// 로그인이 안됐을 경우
		if (userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/login/form";
		}
		
		ReservationResponse reservationResponse
			= reservationService.createReservation(userId, reservationRequest);
		
		if (reservationResponse.isSuccess()) {
			nextPage = "/reservation/reservation_success";
			model.addAttribute("reservationResponse", reservationResponse);
		} else {
			// 실패했을 때 경로
			nextPage = "/reservation/reservation_fail";
		}
//		
//		if(result == null) {
//			// 실패했을 때 경로
//			nextPage = "/reservation/reservation_fail";
//		} else {
//			nextPage = "/reservation/reservation_success";
//		model.addAttribute("result", result);
//	}
	return nextPage;
	}
//}
	
	/**
	 * 예약 정보 불러오기
	 * @param pageNo 페이지 번호
	 * @param model 
	 * @return 예약 정보 목록 페이지
	 */
	@GetMapping("/reservations/pageNo={pageNo}")
	public String getMyReservations(@PathVariable int pageNo, Model model) {
		System.out.println("[ReservationController] getMyReservations");
		
		// 임의로 userId값 1로 설정 (나중엔 세션에서 받아옴)
		long userId = 1;
		
		// 서비스 클래스에서 예약 정보 가져오기
		List<ReservationEntity> result = reservationService.getReservations(userId);
		
		if(result == null) {
			return  "reservationFind_ng";
		}
		
		// Model에 예약 정보 받은것을 JSP파일로 전송
		model.addAttribute("result", result);
		return "/reservation/reservationFind_ok";
	}
	
//	// TODO: 예약 수정(U)
//	@PatchMapping("/reservations")
//	public String changeReservations() {
//		System.out.println("[ReservationController] changeReservations");
//		
//		return "";
//	}

}
	// TODO: 예약 삭제(D)

