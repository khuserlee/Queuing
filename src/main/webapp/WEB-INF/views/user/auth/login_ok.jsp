<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="success-container">
        <h2>로그인 되었습니다!</h2>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/"/>'">홈 화면으로 이동</button>
        </div>
    </div>
</body>
</html>