<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>프로필</title>
<link href="<c:url value='/resources/css/profileStyles.css' />"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<jsp:include page="../../globals/header.jsp" />

		<main>
			<div class="sidebar">
				<ul>
					<li>
						<button type="button" class="sidebar-btn"
							onclick="showSection('reservation')">예약 내역 확인</button>
					</li>
					<li>
						<button type="button" class="sidebar-btn"
							onclick="showSection('edit-info')">회원정보 수정</button>
					</li>
					<li>
						<button type="button" class="sidebar-btn"
							onclick="showSection('delete-account')">회원탈퇴</button>
					</li>
				</ul>
			</div>
			<div class="content">
				<div id="reservation" class="section">
					<h2>예약 내역 확인</h2>
					<p>여기에 예약 내역을 표시합니다.</p>
				</div>
				<div id="edit-info" class="section" style="display: none;">
					<h2>회원정보 수정</h2>
					<form id="updateProfileForm" id="profile-form">
						<div class="input-group">
							<label for="id">아이디</label> <input type="text" id="id" name="id"
								value="${profileUpdateResponse.id}" readonly>
						</div>
						<div class="input-group">
							<label for="name">이름</label> <input type="text" id="name"
								name="name" value="${profileUpdateResponse.name}" required>
						</div>
						<div class="input-group">
							<label for="address">주소</label> <input type="text" id="address"
								name="address" value="${profileUpdateResponse.address}" required>
						</div>
						<div class="input-group">
							<label for="phone">전화번호</label> <input type="text" id="phone"
								name="phone" value="${profileUpdateResponse.phone}" required>
						</div>
						<button type="submit" id="submitBtn">수정하기</button>
					</form>
				</div>
				<div id="delete-account" class="section" style="display: none;">
					<h2>회원탈퇴</h2>
					<div class="profile-actions">
						<form action="${pageContext.request.contextPath}/users/profile"
							method="post" onsubmit="return confirm('정말로 탈퇴하시겠습니까?');">
							<input type="hidden" name="_method" value="DELETE" />
							<button type="submit" class="delete-button">회원탈퇴</button>
						</form>
					</div>
				</div>
			</div>
		</main>

		<jsp:include page="../../globals/footer.jsp" />
	</div>

	<jsp:include page="/resources/js/profileScript_js.jsp" />
	<script>
		const submitBtn = document.getElementById("submitBtn");
		submitBtn.addEventListener('click', onClickEditButton);
		
		function onClickEditButton(event) {
			event.preventDefault();
			
			// 폼 데이터를 수집
			const form = document.getElementById("updateProfileForm");
			const formData = new FormData(form);
	
			// 객체 형태로 데이터를 변환
			const data = {
				userId: ${profileUpdateResponse.userId},
				id: formData.get('id'),
				name: formData.get('name'),
				address: formData.get('address'),
				phone: formData.get('phone')
			};

			fetch ("/queuing/users/profile", {
				method: "PATCH",
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(data)
			})
			.then(response => {
				if (response.ok) {
					return response.text(); // 성공시 JSON 응답 처리
				}
				throw new Error('네트워크 응답에 문제가 있습니다.');
			})
			.then(result => {
				// 서버로부터 받은 결과 처리
				alert('회원정보가 성공적으로 수정되었습니다.');
				window.location.href = result;
			})
			.catch(error => {
				// 에러 처리
				console.error('There was a problem with the fetch operation:', error);
				alert('회원정보 수정에 실패했습니다.');
			});
		}
	</script>
</body>
</html>