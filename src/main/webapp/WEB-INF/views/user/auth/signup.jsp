<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link href="<c:url value='/resources/css/signupStyles.css' />" rel="stylesheet" type="text/css">
	<jsp:include page="/resources/js/loginScript_js.jsp" />
</head>
<body>

<div class="container">
	<jsp:include page="../../globals/header.jsp" />
	
	<main>
		<div class="signup-container">
			<h2>회원가입</h2>
			<form action="<c:url value='/signup' />" name="signup" method="post">
				<div class="input-group">
					<label for="id">아이디</label>
					<input type="text" id="id" name="id" required>
				</div>
				<div class="input-group">
					<label for="password">비밀번호</label>
					<input type="password" id="password" name="password" required>
				</div>
				<div class="input-group">
					<label for="confirmPassword">비밀번호 확인</label>
					<input type="password" id="confirmPassword" name="confirmPassword" required>
				</div>
				<div class="input-group">
					<label for="name">이름</label>
					<input type="text" id="name" name="name" required>
				</div>
				<div class="input-group">
					<label for="address">주소</label>
					<input type="text" id="address" name="address" required>
				</div>
				<div class="input-group">
					<label for="phone">전화번호</label>
					<input type="text" id="phone" name="phone" required>
				</div>
				<button type="submit">회원가입</button>
				<p id="signup_errorMessage" class="error-message">
					<%= request.getAttribute("signupError") != null ? request.getAttribute("signupError") : "" %>
				</p>
			</form>
		</div>
	</main>
	
	<jsp:include page="../../globals/footer.jsp" />
</div>

    <%-- <script src="<%=request.getContextPath()%>/resources/js/signupScript.js"></script> --%>
</body>
</html>
