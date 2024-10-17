<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%-- 	<jsp:include page="../globals/header.jsp" /> --%>
	
	<div id="map"></div>
	
<%-- 	<jsp:include page="../globals/footer.jsp" /> --%>
	<script>
		drawMap("${address}");
	</script>
</body>
</html>