<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>

    <title>메뉴 수정</title>

</head>

<body>

    <h1>메뉴 수정</h1>

    

    <c:if test="${not empty param.menuId}">

        <h2>수정할 메뉴 ID: ${param.menuId}</h2>

        

        <!-- 여기서 메뉴 정보를 가져와서 표시합니다. -->

        <form action="${pageContext.request.contextPath}/menu/update" method="post">

            <input type="hidden" name="menuId" value="${menu.id}"/>

            <label for="name">이름:</label>

            <input type="text" name="name" value="${menu.name}"/><br/>

            <label for="description">설명:</label>

            <input type="text" name="description" value="${menu.description}"/><br/>

            <label for="price">가격:</label>

            <input type="text" name="price" value="${menu.price}"/><br/>

            <button type="submit">수정완</button>

        </form>

    </c:if>

    

    <c:if test="${empty param.menuId}">

        <p>유효한 메뉴 ID가 아닙니다.</p>

    </c:if>

    

    <a href="${pageContext.request.contextPath}/menu/list">목록으로 돌아가기</a>

</body>

</html>