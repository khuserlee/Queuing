<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">



<link href="<c:url value='/resources/css/user/create_account_result.css' />" rel="stylesheet" type="text/css">

</head>
<body>

	<section>
		
		<div id="section_wrap">
		
			<div class="word">
			
				<h3>CREATE ACCOUNT FAIL!!</h3>
				
			</div>
			
			<div class="others">
				
				<a href="<c:url value='/user/member/createAccountForm' />">create account</a>
				<a href="<c:url value='/user/member/loginForm' />">login</a>
				
			</div>
		
		</div>
		
	</section>

	
</body>
</html>