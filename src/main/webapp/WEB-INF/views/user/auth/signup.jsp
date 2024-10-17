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
				<form action="<c:url value='/signup' />" name="signup" id="signup" method="POST">
					<div class="input-group">
						<label for="id">아이디</label>
						<input type="text" id="id" name="id" placeholder="영어 대소문자, 숫자만 가능" required>
					</div>
					<div class="input-group">
						<label for="password">비밀번호</label>
						<input type="password" id="password" name="password" placeholder="영어 대소문자, 숫자, 특수문자 @$!%*?& 만 가능" required>
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
						<div class="addr-container">
							<input type="text" id="address" name="address" readonly placeholder="주소" required>
							<button type="button" id="addrBtn" onclick="execDaumPostcode()">주소 검색</button>
						</div>
					</div>
					<div class="input-group">
						<label for="phone">전화번호</label>
						<input type="text" id="phone" name="phone" placeholder="- 포함" required>
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
		const submitBtn = document.getElementById("submitBtn");
		submitBtn.addEventListener('click', submit);
	
		function submit(event) {
			event.preventDefault();
			
			var formData = getFormData();
			const isValid = validateFormData(formData);
			
			if (!isValid) {
				return;
			}
			
			fetch('/queuing/signup', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(formData)
			})
			.then(response => {
				return response.json();
			})
			.then(data => {
				alert(data.message);
				
				// 성공 시 로그인 화면으로 이동
				if (data.httpStatus === "OK") {
					window.location.href = data.redirectUrl;
				}
			})
			.catch(error => {
				alert(error);
			});
		}
	
		// 유효성 검사
		function validateFormData(formData) {
			// required 속성 검사
			for (const key of Object.keys(formData)) {
				const value = formData[key];
				
				if (!value || value.trim().length === 0) {
					alert("회원가입 양식을 확인해주세요.");
					return false;
				}
				
				if (value != value.trim()) {
					alert("회원가입 양식을 확인해주세요.");
					return false;
				}
				
				return true;
			}
			
			// 전화번호 검사
			const phone = formData['phone'];
			const phoneRegex = /^(01[016789]-\d{3,4}-\d{4}|02-\d{3,4}-\d{4}|\d{2,3}\d{3,4}\d{4})$/;
			
			if (!phoneRegex.test(phone)) {
				alert("전화번호 양식을 확인해주세요.");
				return false;
			}
			
			if (formData['password'] !== formData['confirmPassword']) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			
			return true;
		}
	
		// 폼 데이터 가져오기
		function getFormData() {
			const form = document.getElementById('signup');
			const formData = new FormData(form);
			var result = {};
			
			formData.forEach((value, key) => {
				result[key] = value;
			});
			
			return result;
		}
	
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
