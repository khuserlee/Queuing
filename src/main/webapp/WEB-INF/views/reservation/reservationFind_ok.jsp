<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>예약 조회</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 800px;
	margin: auto;
	background: white;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
	text-align: center;
}

.reservations {
	max-height: 400px;
	overflow-y: auto;
	margin-bottom: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	text-align: center;
	border: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
}

button {
	padding: 10px 15px;
	margin: 0 5px;
	background-color: #007BFF;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

button:hover {
	background-color: #0056b3;
}

.pagination {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<h1>예약 조회 목록</h1>
		<div class="reservations">
			<table>
				<thead>
					<tr>
						<th>예약 번호</th>
						<th>식당 이름</th>
						<th>인원 수</th>
						<th>예약 시간</th>
						<th colspan="1">
					</tr>
				</thead>
				<tbody id="reservationList">
					<c:forEach var="reservation" items="${result}">
						<tr>
							<td>${reservation.reservationNumber}</td>
							<td>${reservation.storeName}</td>
							<td>${reservation.partySize}</td>
							<td>${reservation.reservationTime}</td>

							<td>
								<button onclick="location.href='http://localhost:8090/queuing/reservations/form/${reservation.reservationId}/update'">수정</button>

								<button onclick="deleteReservation(${reservation.reservationNumber})">삭제</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="pagination">
			<button onclick="prevPage()">이전</button>
			<span id="pageNumbers"></span>
			<button onclick="nextPage()">다음</button>
		</div>
	</div>
	<script>
		function print() {
			console.log("클릭");
		}
		function editReservation(reservationId) {
			window.location.href("http://localhost:8090/queuing/reservations")
		}
	
		function deleteReservation(reservationNumber) {
			const url = "/queuing/reservations/" + reservationNumber;
			
			fetch(url, {
				method: "DELETE",	// 요청 메서드 지정
			})
			.then(response => { // 응답 처리
				if (!response.ok) {
					throw new Error("알 수 없는 오류가 발생했습니다");
				}
				// {httpStatus: ok, message: "신창섭", redirectUrl: "https://www.naver.com"}
				return response.json(); //  json형식으로 변환
			})
			.then(data => {
				alert(data.message);
				window.location.href = data.redirectUrl;
			})
			.catch(error => {
				alert(error);
			})
		}

		function prevPage() {
		}

		function nextPage() {
		}
	</script>
</body>
</html>
