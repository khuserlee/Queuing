<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>프로필</title>
    <link href="<c:url value='/resources/css/signup_okStyles.css' />" rel="stylesheet" type="text/css">
	<%-- 나중에 singupStyles로 통합할 것 --%>
</head>
<body>
    <div class="success-container">
        <h2>프로필 제작중 (로그인상태)</h2>
		<form action="<c:url value='/logout' />" method="post">
            <button type="submit" class="logout-button">로그아웃</button>
        </form>
    </div>
</body>
</html>