package com.pyeonrimium.queuing.sms.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pyeonrimium.queuing.reservation.domains.ReservationEntity;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {
	
	private final DefaultMessageService messageService;
	
	// 문자 메시지를 보내는 연락처
	private final String FROM;
	
	// 인증코드 길이
	private final int VERIFICATION_CODE_LENGTH = 6;
	
	// 휴대폰 번호 유효성 평가를 위한 정규식
	private final String PHONE_REGEX = "^(01[0-9])\\d{8}$";

	
	/**
	 * 생성자
	 * @param apiKey coolsms에서 발급받은 api key
	 * @param apiSecretKey coolsms에서 발급받은 secret api key
	 * @param domain
	 * @param from 문자 메시지를 보내는 연락처
	 */
	public SmsService(@Value("#{coolsms_dev['api.key']}") String apiKey,
						@Value("#{coolsms_dev['api.secret.key']}") String apiSecretKey,
						@Value("#{coolsms_dev['domain']}") String domain,
						@Value("#{coolsms_dev['from']}") String from) {
		
		messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, domain);
		FROM = from;
	}
	
	
	/**
	 * 예약 내용을 문자 메시지로 보내기
	 * @param to 문자를 받을 휴대폰 번호
	 * @param reservationEntity 예약 내용
	 */
	public void sendReservationConfirmMessage(String to, ReservationEntity reservation) {

		// 예약 내용
		String userName = reservation.getUserName();
		String storeName = reservation.getStoreName();
		String reservationNumber = reservation.getReservationNumber();
		LocalDate reservationDate = reservation.getReservationDate();
		LocalTime reservationTime = reservation.getReservationTime();
		int partySize = reservation.getPartySize();
		
		// 메시지 내용 작성
		StringBuilder sb = new StringBuilder();
		sb.append("[큐잉(Queuing)]\n")
			.append("안녕하세요, ").append(userName).append("님.\n\n")
			.append("[").append(storeName).append("]").append("\n")
			.append(" 예약이 완료되었습니다.\n")
			.append("- 예약 번호: ").append(reservationNumber).append("\n")
			.append("- 방문 일자: ").append(reservationDate).append("\n")
			.append("- 시간: ").append(reservationTime).append("\n")
			.append("- 인원수: ").append(partySize).append("명").append("\n\n")
			.append("예약 내용을 다시 한 번 확인하시기 바랍니다.").append("\n\n")
			.append("감사합니다!");
		
		// 메시지 보내기
		sendTextMessage(to, sb.toString());
	}
	

	public void sendReservationUpdateMessage(String to, ReservationEntity reservation) {

		// 예약 내용
		String userName = reservation.getUserName();
		String storeName = reservation.getStoreName();
		String reservationNumber = reservation.getReservationNumber();
		LocalDate reservationDate = reservation.getReservationDate();
		LocalTime reservationTime = reservation.getReservationTime();
		int partySize = reservation.getPartySize();
		
		// 메시지 내용 작성
		StringBuilder sb = new StringBuilder();
		sb.append("[큐잉(Queuing)]\n")
			.append("안녕하세요, ").append(userName).append("님.\n\n")
			.append("[").append(storeName).append("]").append("\n")
			.append(" 예약이 수정되었습니다.\n")
			.append("- 예약 번호: ").append(reservationNumber).append("\n")
			.append("- 방문 일자: ").append(reservationDate).append("\n")
			.append("- 시간: ").append(reservationTime).append("\n")
			.append("- 인원수: ").append(partySize).append("명").append("\n\n")
			.append("예약 내용을 다시 한 번 확인하시기 바랍니다.").append("\n\n")
			.append("감사합니다!");
		
		// 메시지 보내기
		sendTextMessage(to, sb.toString());
	}
	
	
	/**
	 * 인증코드 발급 후 문자 메시지 보내기
	 * @param to
	 * @return
	 */
	public String sendVerificationCode(String to) {
		
		// 인증코드 제작
		String verificationCode = createRandomCode(VERIFICATION_CODE_LENGTH);
		
		if (verificationCode == null) {
			return null;
		}
		
		// 문자 내용 작성
		String text = String.format("[큐잉(Queuing)]\n회원가입을 위한 인증번호입니다.\n인증번호: %s", verificationCode);
		
		// 메시지 전송
		SingleMessageSentResponse response = sendTextMessage(to, text);
		
		// 결과 확인
		if (response == null) {
			return null;
		}
		
		System.out.println("인증코드 메시지 발송 처리 결과 => " + response);
		
		return verificationCode;
	}
	
	
	/**
	 * 단일 문자 메시지 전송
	 * @param to 문자 받을 휴대폰 번호
	 * @param text 문자 내용
	 * @return
	 */
	private SingleMessageSentResponse sendTextMessage(String to, String text) {
		// 유효성 검사
		if (!validatePhoneNumber(to)) {
			return null;
		}
		
		// 메시지 작성
		Message message = new Message();
		message.setFrom(FROM);
		message.setTo(to);
		message.setText(text);
		
		// 메시지 전송
		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		
		return response;
	}
	
	
	/**
	 * 휴대폰 번호 유효성 평가
	 * @param str 휴대폰 번호
	 * @return true: 유효함, false: 유효하지 않음
	 */
	private boolean validatePhoneNumber(String str) {
		if (str == null) {
			return false;
		}
		
		String phoneNumber = str.replace("-", "");
		
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
