<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메뉴 목록</title>
	<link href="<c:url value='/resources/css/menus/storeMenus.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<h1>메뉴 목록</h1>
			<table>
				<tr>
					<th>사진</th>
					<th>이름</th>
					<th>가격</th>
					<th>상세정보</th>
					<th>선택</th>
				</tr>
				<c:forEach var="menu" items="${menuListResponse.menus}">
					<tr>
						<td><img src="" alt="Menu Image" /></td>
						<td>${menu.name}</td>
						<td>${menu.price}</td>
						<td>${menu.description}</td>
						<td><input type="checkbox" name="selectedMenuId" value="${menu.menuId}" /></td>
					</tr>
				</c:forEach>
			</table>
			<button onclick="updateMenu()">메뉴 수정</button>
			<button onclick="deleteMenu()">메뉴 삭제</button>
			<button onclick="registerMenu()">메뉴 등록</button>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		let selectedBox = null;
	
		document.querySelectorAll('input[name="selectedMenuId"]').forEach(checkbox => {
			checkbox.addEventListener('change', function() {
				if (selectedBox === null) {
					selectedBox = checkbox;
					return;
				}
				
				if (selectedBox === checkbox) {
					selectedBox = null;
					return;
				}
				
				selectedBox.checked = false;
				selectedBox = checkbox;
			});
		});

		function getSelectedMenuId() {
			if (selectedBox === null) {
				alert('메뉴 수정 또는 삭제를 위해서는 하나의 메뉴를 선택해야 합니다.');
				return -1;
			}
			
			return selectedBox.value;
		}

		function updateMenu() {
			const selectedMenuId = getSelectedMenuId(); // 체크된 메뉴 ID 가져오기
			
			if (selectedMenuId === -1) {
				alert("수정할 메뉴를 하나만 선택해주세요."); // 경고 메시지
			} else {
				const url = '/queuing/menu/updateView/' + selectedMenuId;
				window.location.href = url; // 메뉴 수정 페이지로 이동
			}
		}
		
		function deleteMenu() {
			 const selectedMenuId = getSelectedMenuId();
			 
			if (selectedMenuId === -1) {
				alert("수정할 메뉴를 하나만 선택해주세요.");
			} else {
				const url = '/queuing/menu/delete/' + selectedMenuId;
				window.location.href = url;
			}
		}
		
		function registerMenu() {
			window.location.href = '/queuing/menu/registerView'; // 메뉴 등록 페이지로 이동
		}
	</script>
</body>
</html>