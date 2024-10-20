const calendarElement = document.getElementById('calendar');
const monthDisplay = document.getElementById('month-display');

document.addEventListener('DOMContentLoaded', function() {
    const peopleCountInput = document.getElementById('people'); // 인원수 입력
    const increaseButton = document.getElementById('increase-people');
    const decreaseButton = document.getElementById('decrease-people');

    // 인원수 증가
    increaseButton.addEventListener('click', function() {
        let currentCount = parseInt(peopleCountInput.value);
        peopleCountInput.value = currentCount + 1; // 인원수 증가
    });

    // 인원수 감소
    decreaseButton.addEventListener('click', function() {
        let currentCount = parseInt(peopleCountInput.value);
        if (currentCount > 1) {
            peopleCountInput.value = currentCount - 1; // 인원수 감소
        }
    });
});

function generateCalendar(year, month, today) {
    const firstDay = new Date(year, month).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    let calendarHTML = '<table><thead><tr>';

    // 요일 헤더 추가
    ['일', '월', '화', '수', '목', '금', '토'].forEach(day => {
        calendarHTML += `<th>${day}</th>`;
    });
    calendarHTML += '</tr></thead><tbody>';

    // 현재 날짜 설정
    let currentDay = 1;

    // 6주 동안 반복하여 달력 생성
    for (let week = 0; week < 6; week++) {
        let rowHTML = '<tr>';
        let isEmptyRow = true; // 현재 행이 비어있는지 확인하는 변수

        for (let day = 0; day < 7; day++) {
            if (week === 0 && day < firstDay) {
                // 첫 주의 빈 칸
                rowHTML += '<td class="disabled"></td>';
            } else if (currentDay > daysInMonth) {
                // 마지막 주의 빈 칸
                rowHTML += '<td class="disabled"></td>';
            } else {
                const isPastDate = new Date(year, month, currentDay + 1) < today; // 오늘 날짜 선택 가능
                rowHTML += `<td class="${isPastDate ? 'disabled' : ''}" data-date="${currentDay}">${currentDay}</td>`;
                
                currentDay++;
                isEmptyRow = false; // 현재 행에 날짜가 있으므로 비어있지 않음
            }
        }
        rowHTML += '</tr>';

        // 현재 행이 비어있지 않으면 추가
        if (!isEmptyRow || week < 5) {
            calendarHTML += rowHTML;
        }
    }

    calendarHTML += '</tbody></table>';
    calendarElement.innerHTML = calendarHTML;
    monthDisplay.innerText = `${year}년 ${month + 1}월`;

    // 날짜 클릭 이벤트 추가
    document.querySelectorAll('.calendar td').forEach(td => {
        if (!td.classList.contains('disabled') && td.innerText) {
        	if (td.innerText == today) {
                td.classList.add('selected');
        	}
        }
    });
}