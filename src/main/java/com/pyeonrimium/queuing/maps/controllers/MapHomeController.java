package com.pyeonrimium.queuing.maps.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyeonrimium.queuing.maps.domains.dtos.NearbyStoreResponse;
import com.pyeonrimium.queuing.maps.services.MapService;

@Controller
public class MapHomeController {
	
	@Autowired
	private MapService mapService;
	
	@Value("#{kakao_dev['map.api.key']}")
	private String kakaoMapApiKey;
	
	private final String defaultAddress = "서울 강남구 테헤란로14길 6";

	@GetMapping("/home")
	public String home(Model model) {
		// TODO: 회원 주소 불러오기
		String address = null;
		
		if (address == null) {
			address = defaultAddress;
		}
		
		model.addAttribute("address", address);
		model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
		
		return "maps/mapHome";
	}
	
	/**
	 * 주어진 위도, 경도를 기준으로 가까운 가게 검색
	 * @param latitude 위도
	 * @param longitude 경도
	 * @return 조회된 가게 목록
	 */
	@GetMapping("/map/stores")
	@ResponseBody
	public ResponseEntity<?> getNearbyStores(@RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
		// 주변 가게 목록 조회하기
		NearbyStoreResponse nearbyStoreResponse = mapService.getNearbyStores(latitude, longitude);
		
		if (!nearbyStoreResponse.isSuccess()) {
			return new ResponseEntity<>(nearbyStoreResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.ok(nearbyStoreResponse);
	}
}
