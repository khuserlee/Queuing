<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>예약 관리</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reservation/reservation-manager-list.css'/>">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
			
		<main>
			<h1 id="list-title"></h1>	<!-- 제목 위치 -->
			<div class="reservations" id="reservations"></div>	<!-- 예약 목록 위치 -->
			<div class="page-controller" id="page-controller">
				<button id="prev-page-btn" onclick="prevPage()">이전</button>
				<span id="pagination"></span>
				<button id="next-page-btn" onclick="nextPage()">다음</button>
			</div>
		</main>
			
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script src="<c:url value='/resources/js/reservation/reservation-manager-list.js'/>"></script>
</body>
</html>