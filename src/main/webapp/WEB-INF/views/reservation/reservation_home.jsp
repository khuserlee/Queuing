<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
<<<<<<< HEAD
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

    <script>
        let selectedDate = '';
        let selectedTime = '';
        let personCount = 1;

        function updateSelectedInfo() {
            const selectedInfoDisplay = document.getElementById('selected-info');
            selectedInfoDisplay.innerText = `고객님이 선택하신 날짜와 시간은 ${selectedDate} ${selectedTime}입니다. 인원수는 ${personCount}명입니다.`;
        }

        function increaseCount() {
            personCount++;
            document.getElementById('person-count').value = personCount;
            updateSelectedInfo();
=======
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 신청</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-family: Arial, sans-serif;
        }
        #ReservationHome {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .input-group {
            margin: 10px 0;
        }
        button {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <h1 id="store-id">${store_id}</h1> <!-- DTO로 받아오는 store_id 표시 -->
    
    <form id="ReservationHome" action="<c:url value='/reservation/reservation_home' />" method="post">
        <div class="input-group">
            <label for="date-input">예약 날짜 선택:${reservation_date}</label>
            <input type="date" id="date-input" name="reservationDate" required />
        </div>
        
        <div class="input-group">
            <label for="person-count">인원수:${party_size}</label>
            <button type="button" onclick="decreaseCount()">-</button>
            <input type="number" id="person-count" name="party_size" value="1" min="1" readonly>
            <button type="button" onclick="increaseCount()">+</button>
        </div>

        <div class="input-group">
            <label for="request-textarea">요청사항:</label>
            <textarea id="request-textarea" name="request" rows="4" cols="30"></textarea>
        </div>

        <button type="button" onclick="submitReservation()">예약하기</button>
    </form>

    <script>
        let personCount = 1;

        function increaseCount() {
            personCount++;
            document.getElementById('person-count').value = personCount;
>>>>>>> f58f7c547dfc2c43868c286a5e92f81bdc0d6d8a
        }

        function decreaseCount() {
            if (personCount > 1) {
                personCount--;
                document.getElementById('person-count').value = personCount;
<<<<<<< HEAD
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

        function submitReservation() {
    		let form = document.getElementById("reservation-form");
        	form.submit();
//             const requestTextArea = document.getElementById('request-textarea');
            
//             // 예약 데이터 객체 생성
//             const reservationData = {
//                 date: selectedDate,
//                 time: selectedTime,
//                 partySize: personCount,
//                 request: requestTextArea.value
//             };
            
//             // JSON 문자열로 변환
//             const jsonData = JSON.stringify(reservationData);
            
//             console.log(jsonData);

//             // POST 요청 보내기
//             fetch('<c:url value="/reservation/ReservationConfirm" />', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json' // JSON 형식으로 전송
//                 },
//                 body: jsonData
//             }).then(response => {
//                 if (response.ok) {
//                     return response.json();
//                 }
//                 throw new Error('Network response was not ok.');
//             }).then(data => {
//                 console.log(data); // 서버에서 받은 데이터 콘솔에 출력
//                 alert(data.message); // 서버에서 받은 메시지 표시
//             }).catch(error => {
//                 console.error('There has been a problem with your fetch operation:', error);
//             });
        }

    </script>
</head>

<body>
	<div class="title">
		<h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
	</div>
	<div class="store-name">가게 이름</div>
	<h1>예약 신청</h1>

    <form id="reservation-form" action="<c:url value='/reservation/ReservationConfirm' />" method="post">
        <fieldset name="time">
            <legend>예약 시간 선택</legend>
            <div class="container">
                <div class="time-selection">
                    <input type="date" id="date-input" name="reservationDate" onchange="showTimes()" required />
                </div>
				<div id="times">
					<ul>
						<li><button type="button" onclick="setReservationTime(this)">09:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">10:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">11:00</button></li>
						<li><button type="button" onclick="setReservationTime(this)">12:00</button></li>
					</ul>
					<input type="time" id="reservationTime" name="reservationTime" hidden=true />
				</div>
                <div class="info">
                    <label for="person-count">인원수</label>
                    <button type="button" id="decrease" onclick="decreaseCount()">-</button>
                    <input type="number" id="person-count" name="partySize" value="1" min="1" >
                    <button type="button" id="increase" onclick="increaseCount()">+</button>
                    <br>요청사항<br>
                    <textarea id="request-textarea" name="request" cols="30" rows="5"></textarea>
                </div>
            </div>
            <div id="selected-info"></div>
        </fieldset>
        <br>
        <div class="ReservationButton">
            <input type="button" value="예약" onclick="submitReservation()" />
        </div>
    </form>
</body>
</html>
=======
            }
        }

        function submitReservation() {
            const reservationDate = document.getElementById('date-input').value;
            const requestText = document.getElementById('request-textarea').value;

            // 콘솔 로그에 출력
            console.log(`예약 날짜: ${reservationDate}`);
            console.log(`인원수: ${personCount}`);
            console.log(`요청사항: ${requestText}`);

            // 폼 제출
            const form = document.getElementById('ReservationHome');
            form.submit(); // 서버에 POST 요청 전송
        }
    </script>
</body>
</html>
>>>>>>> f58f7c547dfc2c43868c286a5e92f81bdc0d6d8a
