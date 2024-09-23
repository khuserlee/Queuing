<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
	<%-- 나중에 singupStyles로 통합할 것 --%>
</head>
<body>
    <div class="success-container">
        <h2>새 비밀번호가 문자로 전송됐습니다.</h2>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/login"/>'">홈으로 이동</button>
        </div>
    </div>
</body>
</html>