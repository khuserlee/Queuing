<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pyeonrimium.queuing.stores.domains.dtos.StoreRegistrationResponse" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>매장 상세 정보</title>
	<link href="<c:url value='/resources/css/stores/storeDetail.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<div class="section" id="storeInfo">
				<h1>${storeFindResponse.name}</h1>
				<img class="storeImg" src="">
				<h2>가게 소개</h2>
				<p class="description">
					${storeFindResponse.description}
				</p>
				<h2>가게 정보</h2>
				<table class="details">
					<tr>
						<th>주소</th>
						<td>${storeFindResponse.address}</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td>${storeFindResponse.phone}</td>
					</tr>
					<tr>
						<th>영업시간</th>
						<td>${storeFindResponse.startTime} ~ ${storeFindResponse.endTime}</td>
					</tr>
					<tr>
						<th>휴무일</th>
						<td>${storeFindResponse.closedDay}</td>
					</tr>
				</table>

			</div>
			<div class="section">
				<div>
					<h2>메뉴</h2>
					<ul id="menus">
						<li class="menuItem">
							<div class="menuImg"></div>
							<div class="menuInfo">
								<h3>메뉴 이름</h3>
								<p>3,500원</p>
							</div>
						</li>
					</ul>
				</div>
				<div id="buttons">
					<button type="button">정보 수정</button>
					<button type="button">메뉴 수정</button>
				</div>
			</div>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
</body>
</html>