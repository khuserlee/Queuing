<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<form action="#">
		<div id="tel-section">
			<label for="phone">휴대폰 번호 입력</label>
			<input type="tel" id="phone" placeholder="- 은 빼고 입력하세요." required="required">
			<button type="button" id="send-btn">인증번호 보내기</button>
		</div>
		<div id="verification-section" hidden="true">
			<label for="verification">인증번호</label>
			<input type="text" id="verification">
			<button type="button" id="confirm-btn">인증번호 확인</button>
		</div>
	</form>
	<script>
		const phoneInput = document.getElementById('phone');
		const verificationSection = document.getElementById('verification-section');

		const confirmBtn = document.getElementById('confirm-btn');
		const sendBtn = document.getElementById('send-btn');
		sendBtn.addEventListener('click', send);
		
		function send() {
			console.log("보내기!!");
			const phone = phoneInput.value;
			
			if (!phone) {
				alert('전화번호를 입력하세요.');
				return;
			}
			
			const phoneRegex = /^(01[0-9])\d{8}$/;
			
			if (!phoneRegex.test(phone)) {
				alert('유효한 휴대폰 번호가 아닙니다.');
				return;
			}
			
			fetch('/queuing/sms/verification', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({
					"to" : phone
				})
			})
			.then(response => {
				if (!response.ok) {
					throw new Error("인증번호를 불러올 수 없습니다.");
					sendBtn.addEventListener('click', send);
				}
				return response.json();
			})
			.then(data => {
				console.log("성공: " + data);
				
				function verify() {
					console.log("홀리");
					if (data == document.getElementById('verification').value) {
						isVerified = true;
						confirmBtn.removeEventListener('click', verify);
						alert('인증번호가 일치합니다.');
					} else {
						alert('인증번호가 일치하지 않습니다.');
					}
				}
				
				sendBtn.removeEventListener('click', send);
				confirmBtn.addEventListener('click', verify);
			})
			.catch(error => {
				alert(error);
			});
			
			verificationSection.hidden = false;
		}
		
		var isVerified;
	</script>
</body>
</html>