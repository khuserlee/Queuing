<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>매장 삭제</title>
	<link href="<c:url value='/resources/css/stores/storeDeleteForm.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<form id="form">
				<div>
					<h2>매장을 삭제하겠습니까?</h2>
					<p>삭제된 정보는 복구할 수 없습니다.</p>
					<p>삭제를 하려면 비밀번호를 입력하세요.</p>
				</div>
				<div class="formGroup">
					<label for="password">비밀번호 :</label>
					<input type="password" id="password" name="password" required>
				</div>
				<div class="buttons">
					<button type="submit" id="submitBtn">삭제</button>
					<button type="button" onclick="window.location.href='/queuing/stores/${storeId}'">취소</button>
				</div>
			</form>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		const submitBtn = document.getElementById('submitBtn');
		submitBtn.addEventListener('click', submit);
		
		function submit(event) {
			event.preventDefault();
			
			var formData = getFormData();
			const isValid = validate(formData);
			
			if (!isValid) {
				alert("비밀번호를 입력하세요.");
				return;
			}
			
			fetch('/queuing/users/pwd/check', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(formData)
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("비밀번호가 일치하지 않습니다");
				}
				return response.json();
			})
			.then(data => {
				if (!data.success) {
					throw new Error(data.message);
				}
				return fetch(`/queuing/stores/${storeId}` , {
					method: 'DELETE'
				});
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("매장을 삭제할 수 없습니다.");
				}
				return response.json();
			})
			.then(data => {
				alert(data.message);
				window.location.href=data.redirectUrl;
				
			})
			.catch(error => {
				alert(error);
			});
		}
		
		function validate(formData) {
			for (const key of Object.keys(formData)) {
				const value = formData[key];
				if (!value || value.trim().length === 0) {
					return false;
				}
			}
			
			return true;
		}
		
		function getFormData() {
			const storeForm = document.getElementById('form');
			const formData = new FormData(storeForm);
			var result = {};
			
			formData.forEach((value, key) => {
				result[key] = value;
			});
			
			return result;
		}
	</script>
</body>
</html>