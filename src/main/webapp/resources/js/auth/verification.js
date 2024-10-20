const phoneInput = document.getElementById('phone');
const verificationInput = document.getElementById('verification');

const confirmBtn = document.getElementById('confirm-btn');
const sendBtn = document.getElementById('send-btn');
sendBtn.addEventListener('click', send);

function send() {
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
		phoneInput.disabled = true;
		sendBtn.removeEventListener('click', send);
		
		alert("인증번호를 입력하신 번호로 보냈습니다.");
		
		console.log("성공: " + data);
		
		function verify() {
			if (data == verificationInput.value) {
				isVerified = true;
				confirmBtn.removeEventListener('click', verify);
				alert('인증번호가 일치합니다.');
				verificationInput.disabled = true;
			} else {
				alert('인증번호가 일치하지 않습니다.');
			}
		}
		
		confirmBtn.addEventListener('click', verify);
	})
	.catch(error => {
		alert(error);
	});
	
	verificationInput.disabled = false;
}

var isVerified;