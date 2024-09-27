function drawMap(address) {
	const mapElement = document.getElementById('map');
	resizeMap(mapElement);
	
	var mapContainer = mapElement,
		mapOption = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

	var map = new kakao.maps.Map(mapContainer, mapOption);
	var geocoder = new kakao.maps.services.Geocoder();
	
	geocoder.addressSearch(address, function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
		
			var marker = new kakao.maps.Marker({
				map: map,
				position: coords
			});
			
			map.setCenter(coords);
		
			window.addEventListener('resize', () => resizeMap(mapElement));
		}
	});
}

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