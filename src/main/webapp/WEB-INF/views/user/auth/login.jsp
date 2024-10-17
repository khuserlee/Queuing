<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <link href="<c:url value='/resources/css/loginStyles.css' />" rel="stylesheet" type="text/css">
	<jsp:include page="/resources/js/loginScript_js.jsp" />
</head>
<body>
<div class="container">
	<jsp:include page="../../globals/header.jsp" />
	
	<main>
		<div class="login-container">
			<h2>로그인</h2>
			<form action="<c:url value='/login' />" name="login" method="POST">
				<div class="input-group">
					<label for="ID">아이디</label>
					<input type="text" id="id" name="id" required>
				</div>
				<div class="input-group">
					<label for="password">비밀번호</label>
					<input type="password" id="password" name="password" required>
				</div>
				<button type="submit">로그인</button>
			</form>
			
			<div class="links">
				<a href="<c:url value='/users/find/form'/>">ID/PW 찾기</a>
			</div>
		</div>
	</main>
	
	<jsp:include page="../../globals/footer.jsp" />
</div>
</body>
</html>
