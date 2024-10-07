package com.pyeonrimium.queuing.stores.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.pyeonrimium.queuing.stores.domains.dtos.StoreFindResponse;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegisterationRequest;
import com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;
import com.pyeonrimium.queuing.stores.services.StoreService;

@Controller
public class StoreController {

	// StoreService 연결
	@Autowired
	private StoreService storeService;

	// 매장정보등록 화면 불러오기
	@GetMapping("/stores/form")
	public String getStoreform() {
		System.out.println("[storeControllers] getStoreform()");
		return "/stores/storeRegistration";
	}

	// TODO: 매장 정보 등록(저장)(Create)
	@PostMapping("/stores")
	public String addStore(StoreRegisterationRequest storeRegisterationRequest, HttpSession session, Model model) {
		System.out.println("[storeControllers] addStore()");

		long userId = 1;
		StoreRegistrationResponse storeRegistrationResponse = storeService.addStore(storeRegisterationRequest, userId);

		if (storeRegistrationResponse.isSuccess() == true) {
			model.addAttribute("storeRegistrationResponse", storeRegistrationResponse);
			return "/stores/storeDetail";
		} else {
			// TODO: 실패 페이지 로드(실패 메시지 나오게)
			model.addAttribute("errorMessage", storeRegistrationResponse.getMessage());
			return "store/storefail";
		}

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

	// TODO: 매장 정보 수정(Update)
	@PutMapping("/stores/{storeId}")
	public String updateStore(@PathVariable long storeId) {
		System.out.println("[StoresControllers] updateStore()");
		return "";
	}
	// TODO: 매장 정보 삭제(Delete)
}
