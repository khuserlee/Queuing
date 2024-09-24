<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
역시나 이상한 놈이..  -->
<html>
<head>
<meta charset="UTF-8">
<title>메뉴 등록 수정 버튼 누르면 나오는 화면(하지만 이미 등록된 정보가 없는 등록화면)</title>
</head>
<body>
<h1>${menuId != null ? '메뉴 수정' : '메뉴 등록'}</h1> 
    <form action="${menuId != null ? '/menu/edit' : '/menu/register'}" method="post">
        <input type="hidden" name="menuId" value="${menu.id}" />
        <label>이름:</label>
        <input type="text" name="menuName" value="${menu.name}" />
        <label>가격:</label>
        <input type="text" name="menuPrice" value="${menu.price}" />
        <label>상세정보:</label>
        <textarea name="menuDetails">${menu.details}</textarea>
        <button type="submit">${menuId != null ? '수정' : '등록'}</button>
    </form>
</body>
</html>