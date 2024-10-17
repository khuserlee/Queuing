<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>예약 성공 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="title">
        <h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
    </div>

	<c:choose>
		<c:when test="${reservationResponse.isSuccess()}">
		    <div class="success-message">예약이 성공적으로 완료되었습니다!</div>
		
		    <div class="details">
		        <p>고객님이 선택하신 날짜와 시간:</p>
		        <p id="selected-date-time"></p> <!-- 예시로 날짜와 시간 설정 -->
		        <p>인원수: <span id="selected-person-count">3명</span></p> <!-- 예시로 인원수 설정 -->
		        <p>요청사항: <span id="selected-request">특별 요청 없음</span></p> <!-- 예시로 요청사항 설정 -->
		    </div>
		</c:when>
		<c:otherwise>
		    <div class="title">
		        <h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
		    </div>
		
		    <div class="error-message">예약이 실패했습니다!</div>
		
		    <div class="details">
		        <p>죄송합니다, 고객님의 예약이 처리되지 않았습니다.</p>
		        <p>Error-message : </p>
		    </div>
		</c:otherwise>
	</c:choose>
	
    <div class="button-container">
        <input type="button" value="메인 페이지로 돌아가기" onclick="location.href='index.html';" />
    </div>
</body>
</html>