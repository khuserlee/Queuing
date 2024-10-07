let map;
let currentWindow = null;

const headerHeight = 120;
const footerHeight = 100;
const mainMargin = 40;

function resizeMap(map) {
	const innerHeight = window.innerHeight;
	const height = innerHeight < 600
		? innerHeight - (headerHeight + mainMargin)
		: innerHeight - (headerHeight + footerHeight + 2 * mainMargin);
		
	map.style.height = `${height}px`;
}

var markers = [];

function drawMarker(position) {
	var marker = new kakao.maps.Marker({
		map: map,
		position: position
	});
	
	marker.setMap(map);
	markers.push(marker);
	
	return marker;
}

function clearMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}

function showDetailPage(storeId) {
	location.href = `/queuing/stores/${storeId}`;
}

function closeCurrentInfoWindow() {
	if (currentWindow) {
		currentWindow.close();
	}
}

function setInfoWindows(nearbyStores) {
	clearMarkers();
	
	nearbyStores.forEach(store => {
		const position = new kakao.maps.LatLng(store.latitude, store.longitude);
		const marker = drawMarker(position);
		
		const infowindow = new kakao.maps.InfoWindow({
			content: `
			<div class="info-window">
				<div class="store-img">
					<img src="" alt="">
				</div>
				<div class="store-info">
					<h3>${store.name}</h3>
					<p>${store.address}</p>
					<div class="info-buttons">
						<button onclick="showDetailPage('${store.storeId}')">상세보기</button>
					</div>
				</div>
			</div>`,
		});
		
		// 마커 클릭 시 인포윈도우 표시
		kakao.maps.event.addListener(marker, 'click', function() {
			closeCurrentInfoWindow();
			
			//map.setCenter(marker.getPosition());
			
			infowindow.open(map, marker);
			currentWindow = infowindow;
		});
	});
}

async function searchStores() {
	const center = map.getCenter();
	const lat = center.getLat();
	const lng = center.getLng();
	
	const url = `/queuing/map/stores?latitude=${lat}&longitude=${lng}`;

	try {
		const response = await fetch(url);

		if (!response.ok){
			throw new Error("Error: the network response was not ok");
		}

		const data = await response.json();
		setInfoWindows(data.nearbyStores);
	} catch (error) {
		console.error("Error! => ", error);
	}
}

function drawMap(address) {
	const mapElement = document.getElementById('map');
	resizeMap(mapElement);
	
	var mapContainer = mapElement,
		mapOption = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

	map = new kakao.maps.Map(mapContainer, mapOption);
	var geocoder = new kakao.maps.services.Geocoder();
	
	geocoder.addressSearch(address, function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
			map.setCenter(coords);
			
			searchStores();
		
			window.addEventListener('resize', () => resizeMap(mapElement));
			
			kakao.maps.event.addListener(map, 'dragend', function() {
				searchStores();
			});
		}
	});
}