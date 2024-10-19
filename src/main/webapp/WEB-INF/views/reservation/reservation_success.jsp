<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>예약 성공 페이지</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reservation/reservation-success.css'/>">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
			
		<main>
			<div class="popup">
				<h1 class="success-message">예약이 완료되었습니다!</h1>
				<div class="details">
					<p>고객님께서 예약하신 내용입니다.</p>
					<table>
						<tr>
							<th>업체명</th>
							<td>${reservationResponse.getStoreName()}</td>
						</tr>
						<tr>
							<th>예약자</th>
							<td>${reservationResponse.getUserName()}</td>
						</tr>
						<tr>
							<th>예약일시</th>
							<td>${reservationResponse.getReservationDate()} ${reservationResponse.getReservationTime()}</td>
						</tr>
						<tr>
							<th>인원수</th>
							<td>${reservationResponse.getPartySize()}</td>
						</tr>
						<tr>
							<th>요청사항</th>
							<td>${reservationResponse.getRequest()}</td>
						</tr>
					</table>
				</div>
				<div class="button-container">
					<button onclick="location.href='/queuing/home';">홈으로</button>
				</div>
			</div>
		</main>
			
		<jsp:include page="../globals/footer.jsp" />
	</div>
</body>
</html>