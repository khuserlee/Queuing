package com.pyeonrimium.queuing.sms.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
	
	@Value("#{coolsms_dev['api.key']}")
	private String API_KEY;

	@Value("#{coolsms_dev['secret.api.key']}")
	private String SECRET_API_KEY;
	
	private final int VERIFICATION_CODE_LENGTH = 6;			// 인증코드 길이
	private final String PHONE_REGEX = "^(01[0-9])\\d{8}$";	// 휴대폰 번호 유효성 평가를 위한 정규식
	
	
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
		
		// TODO: 문자 메시지 전송
		
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
