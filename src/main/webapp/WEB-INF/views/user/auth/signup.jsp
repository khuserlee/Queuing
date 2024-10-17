<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>회원가입</title>
	<link href="<c:url value='/resources/css/signupStyles.css' />" rel="stylesheet" type="text/css">
	<jsp:include page="/resources/js/signupScript_js.jsp" />
	<script src="http://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>

	<div class="container">
		<jsp:include page="../../globals/header.jsp" />

		<main>
			<div class="signup-container">
				<h2>회원가입</h2>
				<form action="<c:url value='/signup' />" name="signup" method="POST">
					<div class="input-group">
						<label for="id">아이디</label>
						<input type="text" id="id" name="id" required>
					</div>
					<div class="input-group">
						<label for="password">비밀번호</label>
						<input type="password" id="password" name="password" required>
					</div>
					<div class="input-group">
						<label for="confirmPassword">비밀번호 확인</label>
						<input type="password" id="confirmPassword" name="confirmPassword" required>
					</div>
					<div class="input-group">
						<label for="name">이름</label>
						<input type="text" id="name" name="name" required>
					</div>
					<div class="input-group">
						<label for="address">주소</label>
						<input type="text" id="address" name="address" readonly placeholder="주소" required>
						<button type="button" id="addrBtn" onclick="execDaumPostcode()">주소 검색</button>
					</div>
					<div class="input-group">
						<label for="phone">전화번호</label>
						<input type="text" id="phone" name="phone" required>
					</div>
					<div class="buttons">
						<button type="submit" id="submitBtn">회원가입</button>
					</div>
				</form>
			</div>
		</main>

		<jsp:include page="../../globals/footer.jsp" />
	</div>
	<script>
		function execDaumPostcode() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 검색 결과에서 필요한 정보를 가져옴
					var fullAddr = ''; // 최종 주소 변수
					var extraAddr = ''; // 조합형 주소 변수

					// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져옴
					if (data.userSelectedType === 'R') { // 도로명 주소
						fullAddr = data.roadAddress;
					} else { // 지번 주소
						fullAddr = data.jibunAddress;
					}

					// 사용자가 선택한 주소에 따라 필요한 추가 주소 정보 설정
					if (data.userSelectedType === 'R') {
						if (data.bname !== '') {
							extraAddr += data.bname;
						}
						if (data.buildingName !== '') {
							extraAddr += (extraAddr !== '' ? ', '
									+ data.buildingName : data.buildingName);
						}
						fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')'
								: '');
					}

					// 최종 주소와 우편번호를 각 필드에 넣음
					document.getElementById('address').value = fullAddr; // 주소

					// 상세 주소 입력란에 포커스
// 					document.getElementById('detailAddress').focus();
				}
			}).open();
		}
	</script>
</body>
</html>
