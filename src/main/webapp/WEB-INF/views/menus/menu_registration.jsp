<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메뉴 등록</title>
	<link href="<c:url value='/resources/css/menus/menu_registration.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<h1>메뉴 등록</h1>
			<form id="menu-form" method="post" action="/queuing/menu/register/${storeId}"  enctype="multipart/form-data">
				<div class="form-group">
					<label for="name">메뉴 이름</label>
					<input name="name" type="text" required="required"/>
				</div>
				<div class="form-group">
					<label for="img">가게 사진</label>
					<input type="file" id="file" name="file" accept="image/*">
				</div>
				<div class="form-group">
					<label for="price">가격</label>
					<input name="price" type="number" required="required"/>
				</div>
				<div class="form-group">
					<label for="description">상세 정보</label>
					<input type="text" name="description" />
				</div>
				<div class="buttons">
					<button type="submit" id="submitBtn">등록</button>
					<button type="button" id="cancelBtn">취소</button>
				</div>
			</form>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		const cancelBtn = document.getElementById("cancelBtn");
		cancelBtn.addEventListener('click', cancel);
		
		const submitBtn = document.getElementById("submitBtn");
		submitBtn.addEventListener('click', submit);
		
		function cancel() {
			window.location.href = "/queuing/menu/" + ${storeId};
		}
		
		function submit(event) {
			event.preventDefault();
			
			// 폼 데이터 가져오기
			var formData = getFormData();
			
			// 유효성 검사
			const isValid = validateFormData(formData);
			
			if (!isValid) {
				alert("등록 양식을 확인해주세요.");
				return;
			}
			
			const url = '/queuing/menu/register/' + ${storeId};
			fetch(url, {
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
				if (!data.success) {
					alert(data.message);
					window.location.href = data.redirectUrl;
					return;
				}
				const imageFormData = new FormData();
				imageFormData.append('file', formData['file']);
				
				const url = '/queuing/menu/image/' + ${storeId} + "/" + data.menuId;
				return fetch(url, {
					method: 'POST',
					body: imageFormData
				});
			})
			.then(response => {
				return response.json()
			})
			.then(data => {
				window.location.href = data.redirectUrl;
			})
			.catch(error => {
				alert(error);
			});
		}
		
		// 유효성 검사
		function validateFormData(formData) {
			// required 속성 검사
			for (const key of Object.keys(formData)) {
				if (key === 'file' || key === 'description') {
					continue;
				}
				
				const value = formData[key];
				if (!value || value.trim().length === 0) {
					return false;
				}
			}
			
			return true;
		}
		
		function getFormData() {
			const storeForm = document.getElementById('menu-form');
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