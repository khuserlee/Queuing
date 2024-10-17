<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>예약 성공 페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
body {
	font-family: Arial, sans-serif;
	text-align: center;
	padding: 20px;
}

.success-message {
	font-size: 24px;
	font-weight: bold;
	margin-top: 20px;
	color: green;
}

.details {
	margin-top: 20px;
	font-size: 18px;
}

.button-container {
	margin-top: 30px;
}

.button-container input {
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="title">
		<h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
	</div>

	<div class="success-message">예약이 성공적으로 완료되었습니다!</div>

	<div class="details">
		<p>고객님이 선택하신 날짜와 시간:</p>
		<p id="selected-date-time">${reservationResponse.getReservationDate()} ${reservationResponse.getReservationTime()}</p>
		<p>인원수: <span id="selected-person-count">${reservationResponse.getPartySize()}</span></p>
		<p>요청사항: <span id="selected-request">${reservationResponse.getRequest()}</span></p>
	</div>

	<div class="button-container">
		<input type="button" value="메인 페이지로 돌아가기"
			onclick="location.href='http://localhost:8090/queuing/home';" />
	</div>
</body>
</html>