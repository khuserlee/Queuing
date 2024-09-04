// signupScript.js

document.getElementById('signupForm').addEventListener('submit', function(event) {
  event.preventDefault(); // 기본 제출 동작을 막음

  const id = document.getElementById('id').value;
  const password = document.getElementById('password').value;
  const confirmPassword = document.getElementById('confirmPassword').value;
  const address = document.getElementById('address').value;
  const phone = document.getElementById('phone').value;
  const errorMessage = document.getElementById('errorMessage');

  // 비밀번호 확인
  if (password !== confirmPassword) {
      errorMessage.textContent = '비밀번호가 일치하지 않습니다';
      errorMessage.style.display = 'block';
      return;
  }

  // 하드코딩된 유저 정보 (임시)
  const validID = 'admin';

  // ID 중복 확인 (예시로 하드코딩된 ID 사용)
  if (id === validID) {
      errorMessage.textContent = '이미 사용중인 아이디입니다';
      errorMessage.style.display = 'block';
      return;
  }

  // 회원가입 성공 시
  window.location.href = 'signup-success.html';  // 회원가입 성공 페이지로 리다이렉트
});
