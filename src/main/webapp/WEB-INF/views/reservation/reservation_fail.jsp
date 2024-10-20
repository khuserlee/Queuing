<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>예약 실패</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reservation/reservation-fail.css'/>">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<div class="popup">
				<h1 class="error-message">예약할 수 없습니다.</h1>
				<div class="details">
					<p>죄송합니다. 고객님의 예약이 처리되지 않았습니다.</p>
					<p>${reservationResponse.getMessage()}</p>
				</div>
				<div class="button-container">
					<button onclick="location.href='/queuing/reservations/form/${reservationResponse.getStoreId()}';">다시 예약하기</button>
					<button onclick="location.href='/queuing/home';" id="btn">홈으로</button>
				</div>
			</div>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
</body>
</html>
