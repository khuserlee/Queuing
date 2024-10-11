package com.pyeonrimium.queuing.stores.controllers;

import java.lang.ProcessBuilder.Redirect;

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
import com.pyeonrimium.queuing.stores.domains.dtos.StoreUpdateRequest;
import com.pyeonrimium.queuing.stores.domains.entities.StoreEntity;
import com.pyeonrimium.queuing.stores.services.StoreService;

@Controller
public class StoresControllers {

	//StoreService 연결
	@Autowired
	private StoreService storeService;
	private StoreEntity StoreUpdateRequest;

	
	// 매장정보등록 화면 불러오기
	@GetMapping("/stores/form")
	public String getStoreform() {
		System.out.println("[storeControllers] getStoreform()");
		return "/stores/storeRegistration";
	}
	
	// TODO: 매장 정보  등록(저장)(Create)
	@PostMapping("/stores")
	public String addStore(StoreRegisterationRequest storeRegisterationRequest, HttpSession session, Model model) {
		System.out.println("[storeControllers] addStore()");
		
		long userId = 1;
		StoreRegistrationResponse storeRegistrationResponse = storeService.addStore(storeRegisterationRequest, userId);
		
		if(storeRegistrationResponse.isSuccess() == true) {
//			model.addAttribute("storeRegistrationResponse", storeRegistrationResponse);
//			return "/stores/storeDetail";
			return "redirect:/stores/" + storeRegistrationResponse.getStoreId();
		} else { 
			// TODO: 실패 페이지 로드(실패 메시지 나오게)
			model.addAttribute("errorMessage",storeRegistrationResponse.getMessage());
			return "/stores/storefail";
		}	
	
	}
	
	// TODO: 매장 정보 조회(Read)
	@GetMapping("stores/{storeId}")
	public String findStore(@PathVariable Long storeId, Model model) {
		System.out.println("[StoresControllers] findStore()");
		
		StoreFindResponse storeFindResponse = storeService.findStore(storeId);
		 
		//조회안에 error메시지 나오게
		 if (storeFindResponse.isSuccess() == true) {
			 model.addAttribute("storeFindResponse", storeFindResponse);
			 return "/stores/storeDetail";
		 } else {
			 System.out.println("조회 살패: "  + storeFindResponse.getMessage());
			 return "/stores/storefail";
		 }		
	}

	//@PostMapping("/stores/{storeId}")
//	public String updateStore(@PathVariable Long storeId) {
//		System.out.println("[storesControllers] updateStroe()");
//		
//		storeService.updateStore(storeId);
//		
//		return "/stores/sotreUpdate";
//	}
// TODO: 매장 정보 수정(Update)
	// 매장 정보 수정 폼 불러오기
	@GetMapping("/stores/{storeId}/update")
	public String getStoreEditForm(@PathVariable Long storeId, HttpSession httpSession, Model model) {
	    System.out.println("[StoresControllers] getStoreEditForm()");
	    
	    StoreFindResponse storeFindResponse = storeService.findStore(storeId);
	    
	    if (storeFindResponse.isSuccess()) {
	        model.addAttribute("store", storeFindResponse);
	        return "/stores/storeUpdate";
	    } else {
	        model.addAttribute("errorMessage", storeFindResponse.getMessage());
	        return "/stores/storefail";
	    }
	}

	// 매장 정보 수정 처리
	@PostMapping("/stores/{storeId}")
	public String updateStore(@PathVariable Long storeId, StoreUpdateRequest storeUpdateRequest, HttpSession session, Model model) {
	    System.out.println("[StoresControllers] updateStore()");
	    
	    
	    StoreFindResponse storeUpdateResponse = storeService.updateStore(storeId, storeUpdateRequest);
	    
	    if (storeUpdateResponse.isSuccess()) {
	        return "redirect:/stores/" + storeId;
	    } else {
	        model.addAttribute("errorMessage", storeUpdateResponse.getMessage());
	        return "/stores/storefail";
	    }
	}
	// TODO: 매장 정보 삭제(Delete)
}
