<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>아이디, 비밀번호 찾기</title>
	<link href="<c:url value='/resources/css/find_userInfoStyles.css' />" rel="stylesheet" type="text/css">
	<jsp:include page="/resources/js/find_userInfoScript_js.jsp" />
</head>
<body>
	<div class="container">
		<jsp:include page="../../globals/header.jsp" />
		
		<main>
			<div class="section" id="find-ID">
				<h2>아이디 찾기</h2>
				<form id="findIDForm" action="<%=request.getContextPath()%>/users/find/id" method="POST">
					<div class="input-group">
						<label for="name">이름</label>
						<input type="text" id="name" name="name" required>
					</div>
					<div class="input-group">
						<label for="phone">전화번호</label>
						<input type="text" id="phone" name="phone" required>
					</div>
					<button type="submit">아이디 조회</button>
					<p id="IDErrorMessage" class="error-message"></p>
				</form>
			</div>
	
			<div class="section" id="find-password">
				<h2>비밀번호 찾기</h2>
				<form id="findPasswordForm" action="<c:url value='/users/find/password' />" method="post">
				<div class="input-group">
					<label for="findName">이름</label>
					<input type="text" name=name required>
				</div>
				<div class="input-group">
					<label for="findPhone">전화번호</label>
					<input type="text" name="phone" required>
				</div>
				<div class="input-group">
					<label for="findID">아이디</label>
					<input type="text" name="id" required>
				</div>
				<button type="submit">비밀번호 찾기</button>
				<p id="passwordErrorMessage" class="error-message"></p>
				</form>
			</div>
		</main>
		
		<jsp:include page="../../globals/footer.jsp" />
		
	</div>

	<script src="<%=request.getContextPath()%>/resources/js/find-userInfoScript.js"></script>
</body>
</html>
