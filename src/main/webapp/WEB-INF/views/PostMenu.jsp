<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메뉴 등록</title>
	
	<style>
	.form-group {
		margin-bottom: 15px;
	}
	
	label {
		display: block;
		margin-bottom: 5px;
	}
	
	input[type="text"], input[type="number"], textarea {
		width: 100%;
		padding: 10px;
		font-size: 16px;
	}
	
	button {
		padding: 10px 20px;
		background-color: #007BFF;
		color: white;
		border: none;
		font-size: 16px;
		cursor: pointer;
	}
	
	button:hover {
		background-color: #0056b3;
	}
	</style>
</head>
<body>
	<h1>메뉴 등록</h1>
	<form method="post" action="/queuing/menu/register">
		<div class="form-group">
			<label for="name">메뉴 이름</label> <input name="name" type="text" />
		</div>
		<div class="form-group">
			<label for="price">가격</label> <input name="price" type="number" />
		</div>
		<div class="form-group">
			<label for="description">상세 정보</label> <input type="text"
				name="description" />
		</div>
		<button type="submit">등록</button>
	</form>
</body>
</html>