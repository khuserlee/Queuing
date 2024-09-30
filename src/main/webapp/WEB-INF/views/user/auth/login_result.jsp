<%@page import="com.pyeonrimium.queuing.users.domains.dtos.SignupResponse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>로그인</title>
	<link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="success-container">
		<c:choose>
			<c:when test="${loginResponse.isSuccess()}">
				<h2>로그인 되었습니다!</h2>
				<div class="buttons">
					<button onclick="window.location.href='<c:url value="/"/>'">홈 화면으로 이동</button>
				</div>
			</c:when>
			<c:otherwise>
				<h2>로그인에 실패했습니다.</h2>
				<div class="buttons">
					<button onclick="window.location.href='<c:url value="/login/form"/>'">로그인으로 이동</button>
					<button onclick="window.location.href='<c:url value="/signup/form"/>'">회원가입으로 이동</button>
				</div>
			</c:otherwise>
		</c:choose>
    </div>
</body>
</html>