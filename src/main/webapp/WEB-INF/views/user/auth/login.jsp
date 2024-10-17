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
				<form action="<c:url value='/login' />" name="login" id="login" method="POST">
					<div class="input-group">
						<label for="ID">아이디</label>
						<input type="text" id="id" name="id" required>
					</div>
					<div class="input-group">
						<label for="password">비밀번호</label>
						<input type="password" id="password" name="password" required>
					</div>
					<button type="submit" id="submitBtn">로그인</button>
				</form>
				
				<div class="links">
					<a href="<c:url value='/users/find/form'/>">ID/PW 찾기</a>
				</div>
			</div>
		</main>
		
		<jsp:include page="../../globals/footer.jsp" />
	</div>
	<script>
		const submitBtn = document.getElementById("submitBtn");
		submitBtn.addEventListener('click', submit);
		
		function submit(event) {
			event.preventDefault();
			
			var formData = getFormData();
			const isValid = validateFormData(formData);
			
			if (!isValid) {
				return;
			}
			
			fetch('/queuing/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(formData)
			})
			.then(response => {
				return response.json();
			})
			.then(data => {
				alert(data.message);
				
				// 성공 시 로그인 화면으로 이동
				if (data.success) {
					window.location.href = data.redirectUrl;
				}
			})
			.catch(error => {
				alert(error);
			});
		}
	
		// 유효성 검사
		function validateFormData(formData) {
			// required 속성 검사
			for (const key of Object.keys(formData)) {
				const value = formData[key];
				
				if (!value || value.trim().length === 0) {
					alert("아이디 또는 비밀번호를 확인해주세요.");
					return false;
				}
				
				return true;
			}
			
			return true;
		}
	
		// 폼 데이터 가져오기
		function getFormData() {
			const form = document.getElementById('login');
			const formData = new FormData(form);
			var result = {};
			
			formData.forEach((value, key) => {
				result[key] = value;
			});
			
			return result;
		}
	</script>
</body>
</html>
