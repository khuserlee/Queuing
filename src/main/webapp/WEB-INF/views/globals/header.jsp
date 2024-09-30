<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value='/resources/css/globals/header.css' />"
	rel="stylesheet" type="text/css">

<header>
	<div id="header_wrap">
		<div class="title">
			<h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
		</div>
		<nav class="menu">
			<a class="header_nav_menu" href="<c:url value='/home' />">
				<span>홈으로</span>
			</a>
			<c:choose>
				<c:when test="${user_id == null || user_id == 0}">
					<a class="header_nav_menu" href="<c:url value='/login/form' />">
						<span>로그인</span>
					</a>
					<a class="header_nav_menu" href="<c:url value='/signup/form' />">
						<span>회원가입</span>
					</a>
				</c:when>
				<c:otherwise>
					<a class="header_nav_menu" href="<c:url value='/logout'/>" id="logoutBtn">
						<span>로그아웃</span>
					</a>
					<a class="header_nav_menu" href="<c:url value='/users/mypage' />">
						<span>마이페이지</span>
					</a>
					<c:if test="${role=MANAGER}">
						<a class="header_nav_menu" href="<c:url value='/store' />">
							<span>내 매장 관리</span>
						</a>
						<a class="header_nav_menu" href="<c:url value='/reservations' />">
							<span>예약 관리</span>
						</a>
						<a class="header_nav_menu" href="<c:url value='/waitings' />">
							<span>웨이팅 관리</span>
						</a>
					</c:if>
					<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
					<script>
						$(document).ready(function() {
							$('#logoutBtn').click(function(event) {
								event.preventDefault();
								
								$.post('/queuing/logout', function(response) {
									document.write(response);
								});
							});
						});
					</script>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>
</header>