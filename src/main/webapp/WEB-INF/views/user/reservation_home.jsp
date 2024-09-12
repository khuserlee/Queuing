<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
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
	/* 원하는 높이 설정 */
	text-align: center;
}

.container {
	display: flex;
	justify-content: center;
	/* 중앙 정렬 */
	align-items: flex-start;
}

.time-selection, .info {
	flex: 1;
	/* 동일한 비율로 공간 차지 */
	max-width: 300px;
	/* 최대 너비 설정 */
}

.info {
	margin-left: 20px;
	/* 여백 설정 */
}

#selected-info {
	margin-top: 20px;
	/* 선택한 정보 표시를 위한 여백 */
	font-weight: bold;
	/* 강조를 위한 두껍게 설정 */
}

.underButton {
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
        }

        function decreaseCount() {
            if (personCount > 1) {
                personCount--;
                document.getElementById('person-count').value = personCount;
                updateSelectedInfo();
            }
        }

        function showTimes() {
            selectedDate = document.getElementById('date-input').value;
            const timesContainer = document.getElementById('times');
            timesContainer.innerHTML = ''; // 기존 시간 목록 초기화

            const morningTimes = ['10:00', '10:30', '11:00', '11:30'];
            const afternoonTimes = ['12:00', '12:30', '1:00', '1:30', '2:00', '2:30', '3:00', '3:30', '4:00', '4:30', '5:00', '5:30', '6:00', '6:30', '7:00'];

            const morningSection = document.createElement('div');
            morningSection.innerHTML = '<strong>오전</strong><br>';
            morningTimes.forEach(time => {
                const timeInput = `<input type="button" value="${time}" onclick="selectTime('${time}')">`;
                morningSection.innerHTML += timeInput + ' ';
            });
            timesContainer.appendChild(morningSection);

            const afternoonSection = document.createElement('div');
            afternoonSection.innerHTML = '<strong>오후</strong><br>';
            afternoonTimes.forEach(time => {
                const timeInput = `<input type="button" value="${time}" onclick="selectTime('${time}')">`;
                afternoonSection.innerHTML += timeInput + ' ';
            });
            timesContainer.appendChild(afternoonSection);
        }

        function selectTime(time) {
            selectedTime = time;
            updateSelectedInfo();
        }
    </script>
</head>


<body>
	<div class="title">
		<h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
	</div>
	<div class="store-name">가게 이름</div>
	<h1>예약 신청</h1>
	<fieldset name="time">
		<legend>예약 시간 선택</legend>
		<div class="container">
			<div class="time-selection">
				<input type="date" id="date-input" onchange="showTimes()" />
				<div id="times"></div>
			</div>
			<div class="info">
				<label for="person-count">인원수</label>
				<button type="button" id="decrease" onclick="decreaseCount()">-</button>
				<input type="number" id="person-count" value="1" min="1" readonly>
				<button type="button" id="increase" onclick="increaseCount()">+</button>
				<br>요청사항<br>
				<textarea cols="30" rows="5"></textarea>
			</div>
		</div>
		<div id="selected-info"></div>
		<!-- 선택한 날짜, 시간, 인원수 표시할 공간 -->
	</fieldset>
	<br>
	<div class="underButton">
		<input type="button" value="예약" />
	</div>
</body>

</html>