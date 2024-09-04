// script.js

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 제출 동작을 막음

    const ID = document.getElementById('ID').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');

    // 하드코딩된 유저 정보 (임시)
    const validID = 'admin';
    const validPassword = 'password';

    // 로그인 검증
    if (ID === validID && password === validPassword) {
        // 로그인 성공 시 리다이렉트
        window.location.href = 'homepage.html';  // 이곳에 사용자를 리다이렉트할 페이지를 입력합니다.
    } else {
        // 로그인 실패 시 오류 메시지 표시
        errorMessage.textContent = '아이디 비밀번호를 확인하세요';
        errorMessage.style.display = 'block';
    }
});
