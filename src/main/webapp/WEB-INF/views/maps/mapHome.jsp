<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>큐잉(Queuing)</title>
	<link href="<c:url value='/resources/css/maps/mapHome.css' />"
	rel="stylesheet" type="text/css">
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoMapApiKey}&libraries=services"></script>
	<script src="resources/js/maps/kakaoMapGenerator.js"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<div id="map"></div>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		drawMap("${address}");
	</script>
</body>
</html>