package com.pyeonrimium.queuing.stores.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreEditResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreFindResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegisterationRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse;
import com.pyeonrimium.queuing.stores.services.StoreService;

@Controller
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@Value("#{kakao_dev['map.api.key']}")
	private String kakaoMapApiKey;
	
	
	/**
	 * 내 매장 정보 불러오기
	 * @param session
	 * @return
	 */
	@GetMapping("/stores")
	public String getMyStore(HttpSession session) {
		
		// 로그인이 안 된 경우
		if (session == null) {
			return "redirect:/login/form";
		}
		
		// 일반 유저인 경우
		if (!session.getAttribute("role").equals("MANAGER")) {
			return "redirect:/home";
		}
		
		Long userId = (Long) session.getAttribute("user_id");
		Long storeId = storeService.getMyStoreId(userId);
		
		// 등록한 매장이 없는 경우
		if (storeId == null) {
			return "redirect:/stores/form";
		}
		
		return "redirect:/stores/" + storeId;
	}


	/**
	 * 매장 정보 등록 화면 불러오기
	 * @return 매장 정보 등록 페이지
	 */
	@GetMapping("/stores/form")
	public String getStoreform(Model model) {
		
		model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
		return "/stores/storeRegistration";
	}

	
	/**
	 * 신규 매장 정보 등록하기
	 * @param storeRegisterationRequest 신규 매장 정보
	 * @param session
	 * @param model
	 * @return 성공: 상세 페이지, 실패: 에러 메시지 페이지
	 */
	@PostMapping("/stores")
	public String addStore(@RequestBody StoreRegisterationRequest storeRegisterationRequest, HttpSession session, Model model) {

		// 로그인이 안 된 경우
		if (session == null) {
			return "redirect:/login/form";
		}
		
		// 일반 유저인 경우
		if (!session.getAttribute("role").equals("MANAGER")) {
			return "redirect:/home";
		}

		Long userId = (Long) session.getAttribute("user_id");
		StoreRegistrationResponse storeRegistrationResponse = storeService.addStore(storeRegisterationRequest, userId);

		if (!storeRegistrationResponse.isSuccess()) {
			// 등록 실패 페이지
			model.addAttribute("errorMessage", storeRegistrationResponse.getMessage());
			return "store/storefail";
		}
		
		return "redirect:/stores/" + storeRegistrationResponse.getStoreId();
	}

	/**
	 * 가게 상세 페이지 불러오기
	 * @param storeId 가게 고유 번호
	 * @param model
	 * @return 성공: 가게 상세 페이지, 실패: 실패 페이지
	 */
	@GetMapping("/stores/{storeId}")
	public String findStore(@PathVariable Long storeId, Model model) {

		// 가게 정보 조회
		StoreFindResponse storeFindResponse = storeService.findStore(storeId);

		if (!storeFindResponse.isSuccess()) {
			System.out.println("조회 실패");
			return "";
		}

		model.addAttribute("storeFindResponse", storeFindResponse);
		return "/stores/storeDetail";
	}
	
	
	/**
	 * 매장 정보 수정 페이지 불러오기
	 * @param storeId 가게 고유 번호
	 * @param session
	 * @param model
	 * @return 매장 정보 수정 페이지
	 */
	@GetMapping("stores/{storeId}/form")
	public String getEditForm(@PathVariable Long storeId, HttpSession session, Model model) {

		// 로그인이 안 된 경우
		if (session == null) {
			return "redirect:/login/form";
		}
		
		// 일반 유저인 경우
		if (!session.getAttribute("role").equals("MANAGER")) {
			return "redirect:/home";
		}

		Long userId = (Long) session.getAttribute("user_id");
		// 가게 정보 조회
		StoreFindResponse storeFindResponse = storeService.findStore(storeId, userId);
		
		if (!storeFindResponse.isSuccess()) {
			// TODO: 실패 페이지
			return "";
		}
		
		model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
		model.addAttribute("storeFindResponse", storeFindResponse);
		return "/stores/storeEdit";
	}

	// TODO: 매장 정보 수정(Update)
	@PutMapping("/stores/{storeId}")
	@ResponseBody
	public ResponseEntity<?> updateStore(@PathVariable long storeId,
			@RequestBody StoreEditRequest storeEditRequest,
			HttpSession session) {
		
		// 로그인이 안 된 경우
		if (session == null) {
			StoreEditResponse storeEditResponse = StoreEditResponse.builder()
					.isSuccess(false)
					.message("로그인이 필요합니다.")
					.redirectUrl("/queuing/login/form")
					.build();
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(storeEditResponse);
		}
		
		// 일반 유저인 경우
		if (!session.getAttribute("role").equals("MANAGER")) {
			StoreEditResponse storeEditResponse = StoreEditResponse.builder()
					.isSuccess(false)
					.message("권한이 없습니다.")
					.redirectUrl("/queuing/home")
					.build();
			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(storeEditResponse);
		}

		Long userId = (Long) session.getAttribute("user_id");
		StoreEditResponse storeEditResponse = storeService.editStore(storeId, userId, storeEditRequest);
		
		if (!storeEditResponse.isSuccess()) {
			System.out.println("실패: " + storeEditResponse.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(storeEditResponse.getMessage());
		}

		return ResponseEntity.ok(storeEditResponse);
	}
	
	// TODO: 매장 정보 삭제(Delete)
}
