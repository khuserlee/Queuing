<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>프로필</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/profileStyles.css' />">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reservation/reservation-list.css' />">
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
					<div>
						<div class="reservation-list" id="reservation-list"></div>	<!-- 목록 동적 생성 -->
						<div id="page-controller">
							<button id="prev-page-btn">이전</button>
							<div class="pagination" id="pagination">
							</div>
							<button id="next-page-btn">다음</button>
						</div>
					</div>
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
					<div class="message-section">
						<h3>탈퇴하시겠습니까?</h3>
						<p>삭제된 정보는 복구할 수 없습니다.</p>
						<p>삭제를 하려면 비밀번호를 입력하세요.</p>
					</div>
					<div class="input-group">
						<label for="password">비밀번호</label>
						<input type="password" id="password" name="password" required>
					</div>
					<div class="profile-actions">
						<button type="submit" class="delete-button" id="deleteAccountBtn">회원탈퇴</button>
					</div>
				</div>
			</div>
		</main>

		<jsp:include page="../../globals/footer.jsp" />
	</div>

	<jsp:include page="/resources/js/profileScript_js.jsp" />
	<script src="<c:url value='/resources/js/reservation/reservation-list.js'/>"></script>
	<script>
		// 회원정보 수정 버튼
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

		// 회원탈퇴 버튼
		const deleteAccountBtn = document.getElementById("deleteAccountBtn");
		deleteAccountBtn.addEventListener("click", function() {
			if (!confirm('정말로 탈퇴하시겠습니까?')) {
				return;
			}
			
			const pwd = document.getElementById('password').value;
			const data = { 'password': pwd };
			
			// 비밀번호 확인
			fetch('/queuing/users/pwd/check', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(data)
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("비밀번호를 확인해주세요.");
				}
				return response.json();
			})
			.then(data => {
				if (!data.success) {
					throw new Error(data.message);
				}
				
				// 회원탈퇴
				deleteAccount();
			})
			.catch(error => {
				alert(error);
			});
		});

		function deleteAccount() {
			// fetch API를 사용하여 DELETE 요청을 전송
			fetch("/queuing/users/profile", {
				method: "DELETE",  // HTTP DELETE 메서드 사용
				headers: {
					"Content-Type": "application/json"  // JSON 형식으로 보냄
				}
			})
			.then(response => {
				if (response.ok) {
					return response.json();  // JSON 응답을 받음
				} else {
					throw new Error("탈퇴 요청이 실패했습니다.");
				}
			})
			.then(data => {
				alert(data.message);  // 메시지 출력
				
				if (data.success) {
					// 성공 시 리다이렉트
					window.location.href = data.redirectUrl;
				}
			})
			.catch(error => {
				console.error("에러 발생:", error);
			});
		}
	</script>
</body>
</html>