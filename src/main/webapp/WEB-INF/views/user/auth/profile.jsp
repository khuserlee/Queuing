<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>프로필</title>
    <link href="<c:url value='/resources/css/profileStyles.css' />" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="#" onclick="showSection('reservation')">예약 내역 확인</a></li>
                <li><a href="#" onclick="showSection('edit-info')">회원정보 수정</a></li>
                <li><a href="#" onclick="showSection('review-management')">리뷰 관리</a></li>
            </ul>            
            <form action="<c:url value='/logout' />" method="post">
                <button type="submit" class="logout-button">로그아웃</button>
            </form>
        </div>
        <div class="content">
            <div id="reservation" class="section">
                <h2>예약 내역 확인</h2>
                <p>여기에 예약 내역을 표시합니다.</p>
            </div>
			         <div id="edit-info" class="section" style="display:none;">
			    <h2>회원정보 수정</h2>
			    <form id="updateProfileForm" action="<c:url value='/users/profile' />" method="POST">
			        <input type="hidden" name="_method" value="PATCH">
			        <div class="input-group">
			            <label for="id">아이디</label>
			            <input type="text" id="id" name="id" value="${profileRequest.id}" readonly>
			        </div>
			        <div class="input-group">
			            <label for="name">이름</label>
			            <input type="text" id="name" name="name" value="${profileRequest.name}" required>
			        </div>
			        <div class="input-group">
			            <label for="address">주소</label>
			            <input type="text" id="address" name="address" value="${profileRequest.address}" required>
			        </div>
			        <div class="input-group">
			            <label for="phone">전화번호</label>
			            <input type="text" id="phone" name="phone" value="${profileRequest.phone}" required>
			        </div>
			        <button type="submit">수정 완료</button>
			    </form>
			</div>
            <div id="review-management" class="section" style="display:none;">
                <h2>리뷰 관리</h2>
                <p>여기에 리뷰 목록을 표시합니다.</p>
            </div>
        </div>
    </div>
    
    <jsp:include page="/resources/js/profileScript_js.jsp" />
</body>
</html>