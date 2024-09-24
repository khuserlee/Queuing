<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>임시 비밀번호 발급</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
	<%-- 나중에 singupStyles로 통합할 것 --%>
</head>
<body>
    <div class="success-container">
        <h2>새로운 임시 비밀번호</h2>
           <p><strong>${newPassword}</strong></p>
        <div class="buttons">
			<button onclick="window.location.href='<c:url value="/login"/>'">로그인</button>
        </div>
    </div>
</body>
</html>