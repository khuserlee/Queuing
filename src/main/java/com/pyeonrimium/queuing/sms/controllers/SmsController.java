package com.pyeonrimium.queuing.sms.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pyeonrimium.queuing.sms.services.SmsService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SmsController {
	
	private final SmsService smsService;

	@GetMapping("/sms")
	public String getForm() {
		return "sms/test-form";
	}
	
	@PostMapping("/sms/verification")
	@ResponseBody
	public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, Object> params) {
		String to = (String) params.get("to");
		String verificationCode = smsService.sendVerificationCode(to);
		
		if (verificationCode == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.ok(verificationCode);
	}
}
