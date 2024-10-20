package com.pyeonrimium.queuing.sms.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {
	
	// coolsms를 사용하기 위한 정보
	private final String API_KEY;
	private final String API_SECRET_KEY;
	private final String DOMAIN;
	private final String FROM;
	
	private final DefaultMessageService messageService;
	
	private final int VERIFICATION_CODE_LENGTH = 6;			// 인증코드 길이
	private final String PHONE_REGEX = "^(01[0-9])\\d{8}$";	// 휴대폰 번호 유효성 평가를 위한 정규식

	// 생성
	public SmsService(@Value("#{coolsms_dev['api.key']}") String apiKey,
						@Value("#{coolsms_dev['api.secret.key']}") String apiSecretKey,
						@Value("#{coolsms_dev['domain']}") String domain,
						@Value("#{coolsms_dev['from']}") String from) {
		
		API_KEY = apiKey;
		API_SECRET_KEY = apiSecretKey;
		DOMAIN = domain;
		FROM = from;

		messageService = NurigoApp.INSTANCE.initialize(API_KEY, API_SECRET_KEY, DOMAIN);
	}
	
	
	/**
	 * 인증코드 발급 후 문자 메시지 보내기
	 * @param to
	 * @return
	 */
	public String sendVerificationCode(String to) {
		
		// 유효성 검사
		if (!validatePhoneNumber(to)) {
			return null;
		}
		
		// 인증코드 제작
		String verificationCode = createRandomCode(VERIFICATION_CODE_LENGTH);
		
		if (verificationCode == null) {
			return null;
		}
		
		// TODO: 문자 메시지 전송
		Message message = new Message();
		message.setFrom(FROM);
		message.setTo(to);
		message.setText(String.format("[큐잉(Queuing)]\n회원가입을 위한 인증번호입니다.\n인증번호: %s", verificationCode));
		
		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);
		
		return verificationCode;
	}
	
	/**
	 * 휴대폰 번호 유효성 평가
	 * @param phoneNumber 휴대폰 번호
	 * @return true: 유효함, false: 유효하지 않음
	 */
	private boolean validatePhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			return false;
		}
		
		if (phoneNumber.isBlank()) {
			return false;
		}
		
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();
	}
	
	/**
	 * 인증코드 만들기
	 * @param length 인증코드 길이
	 * @return 인증코드
	 */
	private String createRandomCode(int length) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			int rnd = (int)(Math.random() * 9) + 1;
			sb.append(rnd);
		}
		
		return sb.toString();
	}
}
