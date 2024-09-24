<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>잘못된 계정 정보</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
	<%-- 나중에 singupStyles로 통합할 것 --%>
</head>
<body>
    <div class="success-container">
        <h2>잘못된 계정 정보입니다.</h2>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/signup"/>'">회원가입</button>
			<button onclick="window.location.href='<c:url value="/find_userInfo"/>'">ID/PW 찾기</button>
        </div>
    </div>
</body>
</html>