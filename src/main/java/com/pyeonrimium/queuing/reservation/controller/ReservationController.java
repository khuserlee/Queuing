package com.pyeonrimium.queuing.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyeonrimium.queuing.reservation.Service.ReservationService;
import com.pyeonrimium.queuing.reservation.domains.ReservationDeleteResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;
import com.pyeonrimium.queuing.reservation.domains.ReservationRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationResponse;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateRequest;
import com.pyeonrimium.queuing.reservation.domains.ReservationUpdateResponse;
import com.pyeonrimium.queuing.reservation.domains.dtos.MyReservationListResponse;

@Controller
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	

	/**
	 * 예약 신청 화면 불러오기
	 * @param storeId 가게 고유 ID
	 * @param model
	 * @return 성공: 예약 페이지, 실패: 실패 안내 페이지
	 */
	@GetMapping("/reservations/form/{storeId}")
	public String reservationHome(@PathVariable long storeId, HttpSession session, Model model) {
		
		// 로그인 유무 확인
		Long userId = getUserId(session);
		
		if (userId == null) {
			return "redirect:/login/form";
		}
		
		// storeId를 통해서 storeName 가져오기
		String storeName = reservationService.getStoreName(storeId);

		if (storeName == null) {
			//  조회 실패 처리
			System.out.println("[ReservationController] 가게 이름 조회 실패");
		}

		// model에 가게 이름 저장
		model.addAttribute("storeId", storeId);
		model.addAttribute("storeName", storeName);

		return "reservation/reservation_new";

//	    예약 화면에 필요한 정보 불러오기 => 식당 정보
//	    System.out.println("[ReservationController] getReservationForm");
//	    model.addAttribute("reservationFormResponse", response); // 올바른 변수명 사용

//		return "reservation/reservation_home";
	}

	/**
	 * 로그인 확인
	 * @param session 세션
	 * @return 유저 고유 번호
	 */
	private Long getUserId(HttpSession session) {
		if(session == null) {
			// 로그인 화면으로 리다이렉트
			return null;
		}
		
		// user_id 조회
		Long userId = (Long)session.getAttribute("user_id");
		return userId;
	}


	/**
	 * 예약하기
	 * @param reservationRequest 예약 정보
	 * @param session 세션
	 * @param model
	 * @return
	 */
	@PostMapping("/reservations")
	public String createReservation(@RequestBody ReservationRequest reservationRequest, HttpSession session, Model model) {
		
		// 로그인 확인
		Long userId = getUserId(session);

		// 로그인이 안됐을 경우
		if (userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/login/form";
		}

		ReservationResponse reservationResponse = reservationService.createReservation(userId, reservationRequest);
		model.addAttribute("reservationResponse", reservationResponse);
		
		if (!reservationResponse.isSuccess()) {
			// 실패했을 때 경로
			return "/reservation/reservation_fail";
		}
		
		return "/reservation/reservation_success";
	}
	
	@GetMapping("/reservations")
	public ResponseEntity<?> getReservations(@RequestParam Integer pageNo, HttpSession session) {
		
		// 로그인 확인
		Long userId = getUserId(session);

		// 로그인이 안됐을 경우
		if (userId == null) {
			// 로그인 페이지로 이동
			MyReservationListResponse error = MyReservationListResponse.builder()
					.httpStatus(HttpStatus.UNAUTHORIZED)
					.message("로그인이 필요한 서비스입니다.")
					.redirectUrl("/queuing/login/form")
					.build();
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
		}
		
		MyReservationListResponse response
			= reservationService.getMyReservations(userId, pageNo);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}

	/**
	 * 예약 정보 불러오기
	 * 
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

		if (result == null) {
			return "reservationFind_ng";
		}

		// Model에 예약 정보 받은것을 JSP파일로 전송
		model.addAttribute("result", result);
		return "/reservation/reservationFind_ok";
	}

//	
//	//  예약 수정(U)
//	@PatchMapping("/reservations")
//	public String updateReservations(ReservationUpdateReuqest request, Model model) {
//		System.out.println("[ReservationController] updateReservations");
//		
//		model.addAttribute("request", request);
//		
//		return "예약 수정 페이지";
//	}

	//  예약 수정 페이지 불러오기 (예약된 정보) -> 폼으로 원래 있던 정보 보내기
	@GetMapping("/reservations/form/{reservationId}/update")
	public String getReservationUpdateForm(@PathVariable Long reservationId, Model model) {

		ReservationEntity reservation = reservationService.findReservation(reservationId);

//		System.out.println("request :"+ reservation.getRequest());
//		System.out.println("partysize :" + reservation.getPartySize());

		model.addAttribute("reservation", reservation);
		return "/reservation/reservation_update";
	}

	//  예약 수정 결과(성공, 실패)
	@PatchMapping("/reservations")
	@ResponseBody
	public ResponseEntity<?> updateReservations(@RequestBody ReservationUpdateRequest request) {
		
		System.out.println("[ReservationController] 예약 수정");
		
		// 업데이트한 정보 가져오기
		ReservationUpdateResponse response = reservationService.updateReservations(request);

		if (!response.isSuccess()) {
			// 실패 처리
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		// 성공 처리
		response.setRedirectUrl("/queuing/reservations/pageNo=1");
		return ResponseEntity.ok(response);
	}
	
	// 예약 수정 한 후 신청
	// 예약 삭제(D)
//	@DeleteMapping("/reservations/{reservationNumber}")
//	public String deleteReservation(@PathVariable String ReservationNumber, Model model) {
//		System.out.println("[ReservationController] 예약 삭제");
//		ReservationEntity deleteReservation = reservationService.deleteReservation(ReservationNumber);
//		model.addAttribute("deleteReservation", deleteReservation);
//		return "reservations/pageNo=1";
//	}
	

	/**
	 * 예약 삭제하기
	 * @param reservationNumber 예약번호
	 * @param session 세션
	 * @param model
	 * @return
	 */
	@DeleteMapping("/reservations/{reservationNumber}")
	@ResponseBody
	public ResponseEntity<?> deleteReservation(@PathVariable String reservationNumber, HttpSession session, Model model) {
		ReservationDeleteResponse response = reservationService.deleteReservation(reservationNumber);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}
	
	
	// 뷰 리졸버 -> "new_form.jsp"를 찾아서 html 파일로 전환
	// ↓↓
	// 서블릿(servlet) -> 클라이언트한테 전달
	//  - 응답 객체(ResponseEntity)에 html 파일을 넣어서 응답 객체를 클라이언트한테 보냄
}
