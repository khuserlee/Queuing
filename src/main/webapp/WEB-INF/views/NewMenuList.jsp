<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<button type="button" id="editBtn" name="action" value="edit">메뉴 수정</button>
	<button type="button" id="deleteBtn" name="action" value="delete">메뉴 삭제</button>
	<button type="button" id="addBtn" name="action" value="register">메뉴 등록</button>
	
	<script>
		const editBtn = document.getElementById("editBtn");
		const deleteBtn = document.getElementById("deleteBtn");
		const addBtn = document.getElementById("addBtn");
		
		editBtn.addEventListener("click", editMenu);
		deleteBtn.addEventListener("click", () => {});
		addBtn.addEventListener("click", () => {});
		
		function editMenu() {
		validateSelection()
		
			// 메뉴 수정 화면으로 이동
		}
		
		function deleteMenu() {
			// 메뉴 수정 화면으로 이동
		}
		
		function addMenu() {
			// 메뉴 수정 화면으로 이동
		}
	</script>
</body>
</html>