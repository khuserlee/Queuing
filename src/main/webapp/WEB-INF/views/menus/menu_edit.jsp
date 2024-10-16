<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메뉴 등록</title>
	<link href="<c:url value='/resources/css/menus/menu_edit.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<h1>메뉴 등록</h1>
			<form method="post" action="/queuing/menu/update?storeId=${menuUpdateFormResponse.storeId}&menuId=${menuUpdateFormResponse.menuId}">
				<div class="form-group">
					<label for="name">메뉴 이름</label>
					<input name="name" type="text" value="${menuUpdateFormResponse.name}" required="required"/>
				</div>
				<div class="form-group">
					<label for="img">가게 사진</label>
					<input type="file" id="img" name="img" accept="image/*">
				</div>
				<div class="form-group">
					<label for="price">가격</label>
					<input name="price" type="number" value="${menuUpdateFormResponse.price}" required="required"/>
				</div>
				<div class="form-group">
					<label for="description">상세 정보</label>
					<input type="text" name="description" value="${menuUpdateFormResponse.description}" />
				</div>
				<div class="buttons">
					<button type="submit">수정</button>
					<button type="button" id="cancelBtn">취소</button>
				</div>
			</form>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		const cancelBtn = document.getElementById("cancelBtn");
		cancelBtn.addEventListener('click', cancel);
		
		function cancel() {
			window.location.href = "/queuing/menu/" + ${menuUpdateFormResponse.storeId};
		}
	</script>
</body>
</html>