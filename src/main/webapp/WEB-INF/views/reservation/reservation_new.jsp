<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="<c:url value='/resources/css/reservation/reservation-new.css'/>"  type="text/css">
	<title>예약 신청</title>
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<h1 class="store-name">${storeName}</h1>
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
							<input type="time" id="time" name="time" disabled> <!-- 시간 선택 태그 추가 -->
						</div>
						<div class="time-buttons" id="time-buttons"></div> <!-- 버튼 목록 추가 -->
					</div>
				</div>
				<div class="right-section">
					<div class="people">
						<label for="people">인원수</label>
						<div class="people-controls">
							<button id="decrease-people">-</button>
							<input type="number" id="people" name="people" min="1" value="1"
								readonly>
							<button id="increase-people">+</button>
						</div>
					</div>
					<div class="request">
						<label for="request">요청사항</label>
						<textarea id="request" name="request" rows="9"
							placeholder="특별 요청사항을 입력하세요."></textarea>
					</div>
					<div class="buttons">
						<button type="submit" id="submit-button" onclick="submit(event)">예약하기</button>
						<button type="button" id="cancel-button" onclick="cancel()">취소</button>
					</div>
				</div>
			</div>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script src="<c:url value='/resources/js/reservation/reservation-new.js'/>"></script>
	<script>
		const timeInput = document.getElementById('time');
		const partySizeInput = document.getElementById('people');
		const requestInput = document.getElementById('request');
		
		function cancel() {
			window.location.href = `/queuing/stores/${storeId}`;
		}
		
		function submit(event) {
			event.preventDefault();
			
			// 예약 날짜
			const date = new Date(currentYear, currentMonth, selectedDate);
			date.setHours(date.getHours() + 9);
			
			// 예약 시간
			const time = timeInput.value;
			
			const partySize = partySizeInput.value;
			const request = requestInput.value;
			
			const reservationData = {
				storeId: ${storeId},
				storeName: '${storeName}',
				partySize: partySize,
				request: request,
				reservationDate: date,
				reservationTime: time,
			};
			
			if (!validate(reservationData)) {
				alert("예약 날짜 및 시간을 선택해주세요.");
				return;
			}
			
			fetch('/queuing/reservations', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(reservationData)
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("예약을 할 수 없습니다.");
				}
				return response.text();
			})
			.then(data => {
				document.open();
				document.write(data);
				document.close();
			})
			.catch(error => {
				alert(error);
			});
		}
		
		function validate(data) {
			if (!data['storeId'] || data['storeId'] < 0) {
				return false;
			}
			
			if (!data['storeName']) {
				return false;
			}
			
			if (!data['partySize'] || data['partySize'] < 1) {
				return false;
			}
			
			if (!data['reservationDate']) {
				console.log("Error: " + data['reservationDate']);
				return false;
			}
			
			if (!data['reservationTime']) {
				console.log("Error: " + data['reservationTime']);
				return false;
			}
			
			return true;
		}
	</script>
</body>
</html>
