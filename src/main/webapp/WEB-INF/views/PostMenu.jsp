<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>메뉴 등록</title>

    <style>

        .form-group {

            margin-bottom: 15px;

        }

        label {

            display: block;

            margin-bottom: 5px;

        }

        input[type="text"], input[type="number"], textarea {

            width: 100%;

            padding: 10px;

            font-size: 16px;

        }

        button {

            padding: 10px 20px;

            background-color: #007BFF;

            color: white;

            border: none;

            font-size: 16px;

            cursor: pointer;

        }

        button:hover {

            background-color: #0056b3;

        }

    </style>

</head>

<body>

    <h1>메뉴 등록</h1>

​

    <form:form method="post" action="/menu/register" modelAttribute="menu">

        <div class="form-group">

            <label for="name">메뉴 이름</label>

            <form:input path="name" id="name" type="text" placeholder="메뉴 이름을 입력하세요" required />

        </div>

​

        <div class="form-group">

            <label for="price">가격</label>

            <form:input path="price" id="price" type="number" placeholder="가격을 입력하세요" required />

        </div>

​

        <div class="form-group">

            <label for="description">상세 정보</label>

            <form:textarea path="description" id="description" rows="4" placeholder="메뉴에 대한 상세 정보를 입력하세요" required></form:textarea>

        </div>

​

        <button type="submit">등록</button>

    </form:form>

​

</body>

</html>