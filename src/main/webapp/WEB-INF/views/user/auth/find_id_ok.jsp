<%-- find_password_ng 랑 아예 똑같으면 합쳐도 될듯 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
	<%-- 나중에 singupStyles로 통합할 것 --%>
</head>
<body>
    <div class="success-container">
        <h2>회원님의 아이디</h2>
        <p>고객님의 아이디는: <strong>${foundId}</strong> 입니다.</p>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/login"/>'">로그인</button>
        </div>
    </div>
</body>
</html>