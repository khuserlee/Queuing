<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	function signupForm() {
		console.log('createAccountForm() CALLED!!');
		
		let form = document.signup;
		
		if (form.id.value == '') {
			alert('INPUT USER ID.');
			form.id.focus();
			return false;
			
		} else if (form.password.value == '') {
			alert('INPUT USER password.');
			form.password.focus();
			return false;
			
		} else if (form.confirmPassword.value == '') {
			alert('INPUT USER password AGAIN.');
			form.confirmPassword.focus();
			return false;
			
		} else if (form.password.value != form.confirmPassword.value) {
			alert('비밀번호 불일치');
			form.password.focus();
			return false;
			
		} else if (form.name.value == '') {
			alert('INPUT USER NAME.');
			form.name.focus();
			return false;
			
		} else if (form.gender.value == '') {
			alert('SELECET USER GENDER.');
			form.gender.focus();
			return false;
			
			
		} else if (form.phone.value == '') {
			alert('INPUT USER PHONE.');
			form.phone.focus();
			return false;
			
		} else {
			form.submit();
			
		}
		
	}

</script>