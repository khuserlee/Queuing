<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>등록 실패</title>
	<link href="<c:url value='/resources/css/stores/storeForm.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
    
    <h2>매장 등록 실패</h2>
    <p>${errorMessage}</p>

		<jsp:include page="../globals/footer.jsp" />
	</div>
	</body>
</html>