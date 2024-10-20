const listRoot = document.getElementById('reservations');
const title = document.getElementById('list-title');

const prevPageBtn = document.getElementById('prev-page-btn');
const nextPageBtn = document.getElementById('next-page-btn');
const paginationRoot = document.getElementById('pagination');

var currentPage = 1;

document.addEventListener('DOMContentLoaded', function() {
	fetchItems(currentPage);
});

function fetchItems(page) {
	fetch(`/queuing/reservations/manager?pageNo=${page}`)
	.then(response => {
		if (!response.ok) {
			throw new Error("예약 목록을 불러올 수 없습니다.");
		}
		return response.json();
	})
	.then(data => {
		title.innerHTML = `[${data.storeName}] 예약 목록`;
		showReservations(data.reservations);
		showPagination(data.startPageNo, data.endPageNo, data.currentPageNo, data.lastPageNo);
	})
	.catch(error => {
		alert(error);
	});
}

// 예약 목록 보여주기
function showReservations(reservations) {
	let listHTML = '<table><thead><tr>';
	
	// 헤더 추가
	['예약 번호', '예약자 이름', '인원수', '예약 시간', '요청사항'].forEach(title => {
		listHTML += `<th>${title}</th>`;
	});
	
	listHTML += '</tr></thead><tbody>';
	
	// 예약 내용 표
	reservations.forEach(reservation => {
		var rowHTML = '<tr>';
		
		rowHTML += `<td class="reservatino-number">${reservation.reservationNumber}</td>`;
		rowHTML += `<td>${reservation.userName}</td>`;
		rowHTML += `<td class="party-size">${reservation.partySize}</td>`;
		rowHTML += `<td><p>${reservation.reservationDate}</p><p>${reservation.reservationTime}</p></td>`;
		rowHTML += `<td class="wrapping">${reservation.request}</td>`;
		
		rowHTML += '</tr>';
		
		listHTML += rowHTML;
	});
	
	listHTML += '</tbody></table>';
	
	listRoot.innerHTML = listHTML;
}

// 페이지 표시
function showPagination(startNo, endNo, currentNo, lastPageNo) {
	paginationRoot.innerHTML = '';
	currentPage = currentNo;
	
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