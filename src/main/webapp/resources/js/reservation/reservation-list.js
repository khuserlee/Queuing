const listRoot = document.getElementById('reservation-list');
const prevPageBtn = document.getElementById('prev-page-btn');
const nextPageBtn = document.getElementById('next-page-btn');
const paginationRoot = document.getElementById('pagination');

document.addEventListener('DOMContentLoaded', function() {
	requestReservations(1);
});

async function requestReservations(pageNo) {
	const url = '/queuing/reservations' + '?pageNo=' + pageNo;
	
	try {
		const response = await fetch(url);
		
		if (!response.ok) {
			throw new Error("예약 목록을 불러올 수 없습니다.");
		}
		
		const data = await response.json();
		
		showReservations(data.reservations);
		showPagination(data.startPageNo, data.endPageNo, data.currentPageNo, data.lastPageNo);
	} catch (error) {
		alert(error); 
	}
}

function fetchItems(page) {
	fetch(`/queuing/reservations?pageNo=${page}`)
	.then(response => response.json())
	.then(data => {
		showReservations(data.reservations);
		showPagination(data.startPageNo, data.endPageNo, data.currentPageNo, data.lastPageNo);
	});
}

function showReservations(reservations) {
	let listHTML = '<table><thead><tr>';
	
	// 헤더 추가
	['예약 번호', '식당 이름', '인원수', '예약 시간', ''].forEach(title => {
		listHTML += `<th>${title}</th>`;
	});
	
	listHTML += '</tr></thead><tbody>';
	
	reservations.forEach(reservation => {
		var rowHTML = '<tr>';
		
		rowHTML += `<td>${reservation.reservationNumber}</td>`;
		rowHTML += `<td>${reservation.storeName}</td>`;
		rowHTML += `<td>${reservation.partySize}</td>`;
		rowHTML += `<td>${reservation.reservationDate}<br>${reservation.reservationTime}</td>`;
		
		rowHTML += '<td><div class="td-btn">';
		
		const editUrl = `/queuing/reservations/form/${reservation.reservationId}/update`;
		rowHTML += `<button id="edit-btn" onclick="location.href='editUrl'">수정</button>`;
		
		rowHTML += `<button id="delete-btn" onclick="deleteReservation(${reservation.reservationNumber})">삭제</button>`;
		
		rowHTML += '</div></td>';
		rowHTML += '</tr>';
		
		listHTML += rowHTML;
	});
	
	listHTML += '</tbody></table>';
	
	listRoot.innerHTML = listHTML;
}

function showPagination(startNo, endNo, currentNo, lastPageNo) {
	paginationRoot.innerHTML = '';
	
	// 이전 버튼 설정
	prevPageBtn.disabled = currentNo === 1;
	prevPageBtn.onclick = () => fetchItems(currentNo - 1);
	
	// 다음 버튼 설정
	nextPageBtn.disabled = currentNo === lastPageNo;
	nextPageBtn.onclick = () => fetchItems(currentNo + 1);
	
	// 페이지 표시
	for (let i = startNo; i <= endNo; i++) {
        const pageSpan = document.createElement('span');
        pageSpan.textContent = i;
        
        if (i === currentNo) {
            pageSpan.classList.add('active'); // 현재 페이지 강조
        }
        
        pageSpan.onclick = () => fetchItems(i); // 페이지 클릭 시 이동
		paginationRoot.appendChild(pageSpan);
	}
}
	
function deleteReservation(reservationNumber) {
	const url = "/queuing/reservations/" + reservationNumber;
	
	fetch(url, {
		method: "DELETE",	// 요청 메서드 지정
	})
	.then(response => { // 응답 처리
		if (!response.ok) {
			throw new Error("알 수 없는 오류가 발생했습니다");
		}
		return response.json(); //  json형식으로 변환
	})
	.then(data => {
		alert(data.message);
		window.location.href = data.redirectUrl;
	})
	.catch(error => {
		alert(error);
	})
}