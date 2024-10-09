<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>매장 등록</title>
	<link href="<c:url value='/resources/css/stores/storeForm.css' />" rel="stylesheet" type="text/css">
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoMapApiKey}&libraries=services"></script>
</head>
<body>
	<div class="container">
		<jsp:include page="../globals/header.jsp" />
		
		<main>
			<form id="storeForm" action="<c:url value='/stores' />" method="POST">
				<div>
					<div class="formGroup">
						<label for="name">가게 이름</label>
						<input type="text" id="storeName" name="name" required="required">
					</div>
					<div class="formGroup">
						<label for="img">가게 사진</label>
						<input type="file" id="img" name="img" accept="image/*">
					</div>
					<div class="formGroup">
						<label for="description">가게 소개</label>
						<textarea id="description" name="description" required="required"></textarea>
					</div>
					<div class="formGroup">
						<label for="address">주소</label>
						<input type="text" id="roadAddress" name="roadAddress" placeholder="도로명 주소" readonly="readonly">
						<button type="button" class="btn" onclick="getAddress()">주소 검색</button>
					</div>
					<div class="formGroup">
						<label for="address">상세 주소</label>
						<input type="text" id="detailAddress" name="detailAddress" placeholder="상세 주소">
					</div>
					<div class="formGroup">
						<label for="phone">연락처</label>
						<input type="tel" id="phone" name="phone" required="required">
					</div>
					<div class="formGroup">
						<label for="startTime">시작시간</label>
						<input type="time" id="startTime" name="startTime" required="required">
					</div>
					<div class="formGroup">
						<label for="endTime">마감시간</label>
						<input type="time" id="endTime" name="endTime" required="required">
					</div>
					<div class="formGroup">
						<label for="closedDay">휴무일</label>
						<input type="text" id="closedDay" name="closedDay">
					</div>
				</div>
				<div class="buttons">
					<button type="button" id="deleteBtn">매장 삭제</button>
					<button type="submit" id="submitBtn">매장 등록</button>
					<button onclick="window.location.href='<c:url value="/"/>'">취소</button>

				</div>
			</form>
		</main>
		
		<jsp:include page="../globals/footer.jsp" />
	</div>
	<script>
		const deleteBtn = document.getElementById("deleteBtn");
		deleteBtn.addEventListener('click', () => console.log("delete"));
		
		const submitBtn = document.getElementById("submitBtn");
		submitBtn.addEventListener('click', submit);
		
		function submit(event) {
			event.preventDefault();
			
			var formData = getFormData();
			const isValid = validateFormData(formData);
			
			if (!isValid) {
				alert("등록 양식을 확인해주세요.");
				return;
			}
			
			const address = formData['roadAddress'] + ' ' + formData['detailAddress'];
			formData.address = address;
			
			getLatLng(address)
				.then(({latitude, longitude}) => {
					console.log("lng: " + longitude + ", lat: " + latitude);
					formData.longitude = longitude;
					formData.latitude = latitude;
					
					fetch('/queuing/stores', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json'
						},
						body: JSON.stringify(formData)
					})
					.then(response => {
						if (response.ok) {
							return response.text();
						}
					})
					.then(html => {
						document.open();
						document.write(html);
						document.close();
					})
					.catch(error => {
						alert(error);
					})
				})
				.catch(error => {
					alert(error);
				});
		}
		
		function getLatLng(address) {
			console.log(address);
			return new Promise((resolve, reject) => {
				var geocoder = new kakao.maps.services.Geocoder();

				geocoder.addressSearch(address, function(result, status) {
					if (status === kakao.maps.services.Status.OK) {
						const latitude = result[0].y;  // 위도
						const longitude = result[0].x; // 경도
						
						resolve({ latitude, longitude })
					} else {
						reject('정확한 주소를 입력하세요.');
					}
				});
			});
		}
		
		// 유효성 검사
		function validateFormData(formData) {
			// required 속성 검사
			for (const key of Object.keys(formData)) {
				if (key === 'img' || key === 'detailAddress' || key === 'closedDay') {
					continue;
				}
				
				const value = formData[key];
				if (!value || value.trim().length === 0) {
					return false;
				}
			}
			
			// 전화번호 검사
			const phone = formData['phone'];
			const phoneRegex = /^(01[016789]-\d{3,4}-\d{4}|02-\d{3,4}-\d{4}|\d{2,3}\d{3,4}\d{4})$/;
			
			if (!phoneRegex.test(phone)) {
				return false;
			}
			
			// 영업시간 검사
			if (formData['startTime'] >= formData['endTime']) {
				return false;
			}
			
			return true;
		}
		
		function getFormData() {
			const storeForm = document.getElementById('storeForm');
			const formData = new FormData(storeForm);
			var result = {};
			
			formData.forEach((value, key) => {
				result[key] = value;
			});
			
			return result;
		}
		
		function getAddress() {
			new daum.Postcode({
				oncomplete: function(data) {
					document.getElementById('roadAddress').value = data.roadAddress;
				}
			}).open();
		}
	</script>
</body>
</html>