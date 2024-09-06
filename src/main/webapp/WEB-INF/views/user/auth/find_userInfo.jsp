<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%-- <title>필요시 입력</title>--%>
    <%-- 타이틀, 푸터는 추후에 include로 대체 예정 --%>
    <link href="<c:url value='/resources/css/find_userInfoStyles.css' />" rel="stylesheet" type="text/css">
	<%-- css가 왜 적용 안되는지 모르겠음 --%>
	<jsp:include page="../../../../resources/js/find_userInfoScript_js.jsp" />
	
</head>
    <div class="container">
        <div class="section" id="find-ID">
            <h2>아이디 찾기</h2>
            <form id="findIDForm" action="<%=request.getContextPath()%>/findID" method="post">
                <div class="input-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" required>
                </div>
                <div class="input-group">
                    <label for="phone">전화번호</label>
                    <input type="text" id="phone" name="phone" required>
                </div>
                <button type="submit">아이디 조회</button>
                <p id="IDErrorMessage" class="error-message"></p>
            </form>
        </div>

        <div class="section" id="find-password">
            <h2>비밀번호 찾기</h2>
            <form id="findPasswordForm" action="<%=request.getContextPath()%>/findPassword" method="post">
                <div class="input-group">
                    <label for="findName">이름</label>
                    <input type="text" id="findName" name="findName" required>
                </div>
                <div class="input-group">
                    <label for="findPhone">전화번호</label>
                    <input type="text" id="findPhone" name="findPhone" required>
                </div>
                <div class="input-group">
                    <label for="findID">아이디</label>
                    <input type="text" id="findID" name="findID" required>
                </div>
                <button type="submit">비밀번호 찾기</button>
                <p id="passwordErrorMessage" class="error-message"></p>
            </form>
        </div>
    </div>

    <script src="<%=request.getContextPath()%>/resources/js/find-userInfoScript.js"></script>
</body>
</html>
