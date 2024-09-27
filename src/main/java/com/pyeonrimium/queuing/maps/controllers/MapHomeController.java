package com.pyeonrimium.queuing.maps.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapHomeController {
	
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
	
}
