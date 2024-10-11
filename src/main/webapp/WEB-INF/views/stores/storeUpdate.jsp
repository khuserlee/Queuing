<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>매장 정보 수정</title>
    <link href="<c:url value='/resources/css/stores/storeDetail.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <h1>매장 정보 수정 </h1>
        <form action="<c:url value='/stores/{storeId}' />" method="POST">
            <div class="form-group">
                <label for="name">가게 이름</label>
                <input type="text" id="name" name="name" value="${store.name}" required>
            </div>
            <div class="form-group">
                <label for="description">가게 소개</label>
                <textarea id="description" name="description" required>${store.description}</textarea>
            </div>
            <div class="form-group">
                <label for="address">주소</label>
                <input type="text" id="address" name="address" value="${store.address}" required>
            </div>
            <div class="form-group">
                <label for="phone">연락처</label>
                <input type="tel" id="phone" name="phone" value="${store.phone}" required>
            </div>
            <div class="form-group">
                <label for="startTime">시작시간</label>
                <input type="time" id="startTime" name="startTime" value="${store.startTime}" required>
            </div>
            <div class="form-group">
                <label for="endTime">마감시간</label>
                <input type="time" id="endTime" name="endTime" value="${store.endTime}" required>
            </div>
            <div class="form-group">
                <label for="closedDay">휴무일</label>
                <input type="text" id="closedDay" name="closedDay" value="${store.closedDay}">
            </div>
            
            <div class="form-group">
                <button type="submit">정보 수정</button>
                <a href="<c:url value='/stores/${store.storeId}' />">취소</a>
            </div>
        </form>
    </div>
</body>
</html>