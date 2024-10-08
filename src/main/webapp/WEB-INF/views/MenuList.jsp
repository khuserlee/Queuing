<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<!DOCTYPE html><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>

    <title>메뉴 목록</title>

</head>

<body>
    <h1>메뉴 목록</h1> 
    <table>
    <tr>
        <th>사진</th>
        <th>이름</th>
        <th>가격</th>
        <th>상세정보</th>
        <th>선택</th>
    </tr>
    <c:forEach var="menu" items="${menuList}">
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

<script>
function updateMenu(){
	window.location.href = '/queuing/menu/updateView/{selectedMenuId}'; 
	onsubmit 방식 
<!--	GetMapping("/menu/update/{menuId}")
	public String updateMenu(@PathVariable Long menuId) {
		// menuId로 데이터 조회하기
		// model에 addAttribute로 저장하기
		
		return "수정 페이지";
	}

	@PostMapping("/menu/update")
	public String updateMenu(@RequestParam Long menuId) {
		
	}

	function updateMenu() {
		const selectedMenuIds = [...document.querySelectorAll('input[name="selectedMenuId"]:checked')].map(checkbox => checkbox.value);
		
		
		 // 서버로 전송
	    if (selectedMenuIds.length === 1) {
	        // 필요한 데이터만 서버로 POST 요청
	        fetch('/queuing/menu/updateView', {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json',
	            },
	            body: JSON.stringify({ selectedMenuId: selectedMenuIds[0] }),
	        }).then(response => {
	            if (response.ok) {
	                window.location.href = '/queuing/menu/updateView'; // 서버가 리다이렉션을 반환하지 않는다면 직접 이동
	            }
	        });
	    } else {
	        alert('하나의 메뉴만 선택해 주세요.');
	    } -->
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

function deleteMenu() {
    
}

function registerMenu() {
    window.location.href = '/queuing/menu/registerView'; // 메뉴 등록 페이지로 이동
}
</script>

</body>
</html>