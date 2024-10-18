<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="styles.css">
	<title>예약 신청</title>
</head>
<body>
	<div class="container">
		<h1 class="store-name">가게 이름</h1>
		<main>
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
					<button type="submit" id="submit-button">예약하기</button>
					<button type="button" id="cancel-button">취소</button>
				</div>
			</div>
		</main>
	</div>
	<script src="script.js"></script>
</body>
</html>
