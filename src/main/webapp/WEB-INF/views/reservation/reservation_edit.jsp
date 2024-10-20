<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="<c:url value='/resources/css/reservation/reservation-edit.css'/>"  type="text/css">
	<title>예약 신청</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<h1 class="store-name">${reservationEditResponse.getStoreName()}</h1>
			<div class="reservation-view">
				<div class="left-section">
					<div class="calendar">
						<div class="calendar-controls">
							<button id="prev-month" class="disabled" disabled>◀</button>
							<span id="month-display"></span>
							<button id="next-month" class="disabled" disabled>▶</button>
						</div>
						<div id="calendar"></div>
					</div>
					<div class="time">
						<div class="time-input">
							<label for="time">시간 선택</label>
							<input type="time" id="time" name="time" value="${reservationEditResponse.getReservationTime()}" disabled> <!-- 시간 선택 태그 추가 -->
						</div>
						<div class="time-buttons" id="time-buttons">
							<button class="time-button selected">${reservationEditResponse.getReservationTime()}</button>
						</div>
					</div>
				</div>
				<div class="right-section">
					<div class="people">
						<label for="people">인원수</label>
						<div class="people-controls">
							<button id="decrease-people">-</button>
							<input type="number" id="people" name="people" min="1" value="${reservationEditResponse.getPartySize()}" readonly>
							<button id="increase-people">+</button>
						</div>
					</div>
					<div class="request">
						<label for="request">요청사항</label>
						<textarea id="request" name="request" rows="9" placeholder="특별 요청사항을 입력하세요.">${reservationEditResponse.getRequest()}</textarea>
					</div>
					<div class="buttons">
						<button type="button" id="submit-button" onclick="submit(event)">수정하기</button>
						<button type="button" id="cancel-button" onclick="cancel()">취소</button>
					</div>
				</div>
			</div>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script src="<c:url value='/resources/js/reservation/reservation-edit.js'/>"></script>
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			const date = "${reservationEditResponse.getReservationDate()}";
			const [year, month, day] = date.split('-');
			
			generateCalendar(parseInt(year, 10), (parseInt(month, 10) - 1), parseInt(day, 10));
		});
		
		function cancel() {
			window.location.href = '/queuing/users/profile';
		}
		
		function submit(event) {
			event.preventDefault();

			const partySizeInput = document.getElementById('people');
			const requestInput = document.getElementById('request');
			
			const partySize = partySizeInput.value;
			const request = requestInput.value;
			
			const editedData = {
				reservationId: ${reservationEditResponse.getReservationId()},
				partySize: partySize,
				request: request
			};
			
			fetch('/queuing/reservations', {
				method: 'PATCH',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(editedData)
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("예약을 수정할 수 없습니다.");
				}
				return response.json();
			})
			.then(data => {
				window.location.href = data.redirectUrl;
			})
			.catch(error => {
				alert(error);
			});
		}
	</script>
</body>
</html>
