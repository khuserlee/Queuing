<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입 성공</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="success-container">
        <h2>회원가입을 완료했습니다!</h2>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/login"/>'">로그인으로 이동</button>
        </div>
    </div>
</body>
</html>