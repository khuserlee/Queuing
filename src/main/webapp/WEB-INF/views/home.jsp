<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <meta charset="UTF-8">
</head>
<body>
<h1>
    Hello world!
</h1>

<p>The time on the server is ${serverTime}.</p>

<a href="<c:url value='/login/form' />">로그인</a>
<a href="<c:url value='/users/profile' />">프로필</a>

</body>
</html>
