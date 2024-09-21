<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
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
        }

        function decreaseCount() {
            if (personCount > 1) {
                personCount--;
                document.getElementById('person-count').value = personCount;
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
