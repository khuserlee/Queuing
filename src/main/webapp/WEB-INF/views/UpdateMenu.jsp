<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>

    <title>메뉴 수정</title>

</head>

<body>

    <h1>메뉴 수정</h1>

    

        <h2>수정할 메뉴 </h2>

        

     

        <form action="/queuing/menu/update" method="post">

            <input type="hidden" name="menuId" value="${menu.id}"/>

            <label for="name">이름:</label>

            <input type="text" name="name" value="${menu.name}"/><br/>

            <label for="description">설명:</label>

            <input type="text" name="description" value="${menu.description}"/><br/>

            <label for="price">가격:</label>

            <input type="text" name="price" value="${menu.price}"/><br/>

            <button type="submit">수정 완료</button>

        </form>

   

    


   

</body>

</html>