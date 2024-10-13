<%@page import="com.pyeonrimium.queuing.users.domains.dtos.SignupResponse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	<link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="success-container">
		<h2>${signupResponse.getMessage()}</h2>
		<div class="buttons">
			<c:choose>
				<c:when test="${signupResponse.isSuccess()}">
					<button onclick="window.location.href='<c:url value="/login/form"/>'">로그인</button>
				</c:when>
				<c:otherwise>
					<button onclick="window.location.href='<c:url value="/"/>'">홈으로</button>
					<button onclick="window.location.href='<c:url value="/signup/form"/>'">회원가입</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>