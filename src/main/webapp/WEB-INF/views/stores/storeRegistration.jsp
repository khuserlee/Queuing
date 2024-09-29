<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>매장 등록</title>
	<link href="<c:url value='/resources/css/stores/storeForm.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<form action="<c:url value='/stores' />" method="POST">
				<div>
					<div class="formGroup">
						<label for="name">가게 이름</label>
						<input type="text" id="storeName" name="name" required="required">
					</div>
					<div class="formGroup">
						<label for="img">가게 사진</label>
						<input type="file" id="storeName" name="img" accept="image/*">
					</div>
					<div class="formGroup">
						<label for="description">가게 소개</label>
						<textarea id="storeName" name="description" required="required"></textarea>
					</div>
					<div class="formGroup">
						<label for="address">주소</label>
						<input type="text" id="storeName" name="address" required="required">
					</div>
					<div class="formGroup">
						<label for="phone">연락처</label>
						<input type="tel" id="storeName" name="phone" required="required">
					</div>
					<div class="formGroup">
						<label for="startTime">시작시간</label>
						<input type="time" id="storeName" name="startTime" required="required">
					</div>
					<div class="formGroup">
						<label for="endTime">마감시간</label>
						<input type="time" id="storeName" name="endTime" required="required">
					</div>
					<div class="formGroup">
						<label for="closedDay">휴무일</label>
						<input type="text" id="storeName" name="closedDay">
					</div>
				</div>
				<div class="buttons">
					<button type="button" id="deleteBtn">매장 삭제</button>
					<button type="submit">매장 등록</button>
					<button onclick="window.location.href='<c:url value="/"/>'">취소</button>

				</div>
			</form>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		const deleteBtn = document.getElementById("deleteBtn");
		deleteBtn.addEventListener('click', () => console.log("delete"));
	</script>
</body>
</html>