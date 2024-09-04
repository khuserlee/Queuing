// find-userInfoScript.js

document.getElementById('findIDForm').addEventListener('submit', function(event) {
  event.preventDefault(); // 기본 제출 동작을 막음

  const name = document.getElementById('name').value;
  const phone = document.getElementById('phone').value;
  const errorMessage = document.getElementById('IDErrorMessage');

  // 하드코딩된 유저 정보 (임시)
  const validName = 'John Doe';
  const validPhone = '123-456-7890';

  // 아이디 찾기 검증
  if (name === validName && phone === validPhone) {
      // 아이디 찾기 성공 시
      alert('ID: admin'); // 실제 응답 처리 필요
  } else {
      // 아이디 찾기 실패 시
      errorMessage.textContent = '가입 정보가 없습니다. 입력한 내용을 확인하세요.';
      errorMessage.style.display = 'block';
  }
});

document.getElementById('findPasswordForm').addEventListener('submit', function(event) {
  event.preventDefault(); // 기본 제출 동작을 막음

  const findName = document.getElementById('findName').value;
  const findPhone = document.getElementById('findPhone').value;
  const findID = document.getElementById('findID').value;
  const errorMessage = document.getElementById('passwordErrorMessage');

  // 하드코딩된 유저 정보 (임시)
  const validName = 'John Doe';
  const validPhone = '123-456-7890';
  const validID = 'admin';

  // 비밀번호 찾기 검증
  if (findName === validName && findPhone === validPhone && findID === validID) {
      // 비밀번호 찾기 성공 시
      alert('Password: password'); // 실제 응답 처리 필요
  } else {
      // 비밀번호 찾기 실패 시
      errorMessage.textContent = '가입 정보가 없습니다. 입력한 내용을 확인하세요.';
      errorMessage.style.display = 'block';
  }
});
