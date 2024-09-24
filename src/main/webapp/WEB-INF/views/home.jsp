<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>
    Hello world!
</h1>

<p>The time on the server is ${serverTime}.</p>

<!-- Spring MVC Controller를 통해 /login URL로 이동 -->
<form action="${pageContext.request.contextPath}/login/form" method="get">
    <button type="submit">Login</button>
</form>

</body>
</html>
