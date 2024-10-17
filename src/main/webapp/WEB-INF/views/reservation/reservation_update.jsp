<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">

<title>예약신청 페이지</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
.store-name {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100px;
	text-align: center;
}

.container {
	display: flex;
	justify-content: center;
	align-items: flex-start;
}

.time-selection, .info {
	flex: 1;
	max-width: 300px;
}

.info {
	margin-left: 20px;
}

#selected-info {
	margin-top: 20px;
	font-weight: bold;
}

.ReservationButton {
	display: flex;
	justify-content: right;
}
</style>
</head>

<body>
	<div class="title">
		<h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
	</div>
	<div class="store-name">${storeName}</div>
	<h1>예약 신청</h1>

	<form id="reservation-form">
		<input type="text" name="storeId" value="${storeId}"
			readonly="readonly" hidden="true" />
		<fieldset name="time">
			<legend>예약 시간 선택</legend>
			<div class="container">
				<div class="time-selection">
					<input type="date" id="date-input" name="reservationDate"
						value="${reservation.reservationDate}" onchange="showTimes()"
						required />
				</div>
				<div id="times">
					<ul>
						<li><button type="button" onclick="setReservationTime(this)">09:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">10:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">11:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">12:00</button></li>
					</ul>
					<input type="time" id="reservationTime" name="reservationTime"
						hidden="true" />
				</div>
				<div class="info">
					<label for="person-count">인원수</label>
					<button type="button" id="decrease" onclick="decreaseCount()">-</button>
					<input type="number" id="person-count" name="partySize"
						value="${reservation.partySize}" min="1">
					<button type="button" id="increase" onclick="increaseCount()">+</button>
					<br>요청사항<br>
					<textarea id="request-textarea" name="request" cols="30" rows="5">${reservation.request}</textarea>
				</div>
			</div>
			<div id="selected-info"></div>
		</fieldset>
		<br>
		<div class="ReservationButton">
			<button type="submit">수정</button>
		</div>
	</form>

	<script>
		let selectedDate = '';
		let selectedTime = '';
		let personCount = 1;

		function updateSelectedInfo() {
			const selectedInfoDisplay = document
					.getElementById('selected-info');
			selectedInfoDisplay.innerText = `고객님이 선택하신 날짜와 시간은 ${selectedDate} ${selectedTime}입니다. 인원수는 ${personCount}명입니다.`;
		}

		function increaseCount() {
			personCount++;
			document.getElementById('person-count').value = personCount;
			updateSelectedInfo();

		}

		function decreaseCount() {
			if (personCount > 1) {
				personCount--;
				document.getElementById('person-count').value = personCount;

				updateSelectedInfo();
			}
		}

		function showTimes() {
			const storeId = "store_id";
			//             selectedDate = document.getElementById('date-input').value;
			//             const timesContainer = document.getElementById('times');
			//             timesContainer.innerHTML = ''; // 기존 시간 목록 초기화

		}

		function selectTime(time) {
			selectedTime = time;
			updateSelectedInfo();
		}

		let reservationTime = null;
		function setReservationTime(btn) {
			reservationTime = btn.innerText;

			const input = document.getElementById("reservationTime");
			input.value = reservationTime;
			console.log("time: " + input.value);
		}

		function submitReservation(event) {
			event.preventDefault();

			let form = document.getElementById("reservation-form");
			fetch("/queuing/reservations", {
				header: 'Content-Type: '
			})
			form.submit();
		}

		// id가 reservation-form인 아이디를 찾아 객체를 반환
		document.getElementById("reservation-form").addEventListener('submit', function(event) {
			event.preventDefault(); // 기본 폼 제출 동작 방지

			const formData = new FormData(this); // 폼 데이터 가져오기
			var data = {};
			
			formData.forEach((value, key) => {
				data[key] = value;
			});

		    fetch("/queuing/reservations", {
		        method: 'PATCH', // 또는 'PATCH' 등 필요한 HTTP 메서드
		        headers: {
		        	'Content-Type': 'application/json',
		        },
		        body: JSON.stringify(data), // 폼 데이터 전송
		    })
		    .then(response => {
		        return response.json(); // JSON 응답으로 변환
		    })
		    .then(data => {
		    	if (!data.success) {
		    		throw new Error(data.message);
		    	}
		        console.log(data); // 성공 시 처리
		        // 예약 성공 페이지로 리다이렉트하거나 메시지 표시
		        window.location.href = data.redirectUrl;
		    })
		    .catch(error => {
		        alert(error);
		    });
		});
	</script>
</body>
</html>

