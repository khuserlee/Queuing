document.addEventListener('DOMContentLoaded', function() {
    const calendarElement = document.getElementById('calendar');
    const monthDisplay = document.getElementById('month-display');
    const prevMonthButton = document.getElementById('prev-month');
    const nextMonthButton = document.getElementById('next-month');

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


    
    const timeInput = document.getElementById('time');
    const timeButtonsContainer = document.getElementById('time-buttons');

    // 09:00부터 17:00까지 1시간 간격 버튼 생성
    for (let hour = 9; hour <= 17; hour++) {
        const button = document.createElement('button');
        const formattedTime = hour < 10 ? `0${hour}:00` : `${hour}:00`; // 시간 포맷
        button.textContent = formattedTime;
        button.classList.add('time-button');
        button.dataset.time = formattedTime; // 버튼에 시간 데이터 추가

        // 버튼 클릭 이벤트
        button.addEventListener('click', function() {
            timeInput.value = formattedTime; // 입력 필드에 시간 설정
            updateButtonStates(formattedTime); // 버튼 상태 업데이트
        });

        timeButtonsContainer.appendChild(button);
    }

    // 버튼 상태 업데이트 함수
    function updateButtonStates(selectedTime) {
        const buttons = document.querySelectorAll('.time-button');
        buttons.forEach(button => {
            if (button.dataset.time === selectedTime) {
                button.classList.add('selected'); // 선택된 시간 버튼에 'selected' 클래스 추가
            } else {
                button.classList.remove('selected'); // 나머지 버튼에서 'selected' 클래스 제거
            }
        });
    }



    let today = new Date();
    let currentMonth = today.getMonth();
    let currentYear = today.getFullYear();
    let selectedDate = null;

    function updateButtons() {
        // 이전 달과 다음 달 버튼 활성화/비활성화
        if (currentYear === today.getFullYear() && currentMonth === today.getMonth()) {
            prevMonthButton.classList.add('disabled');
            prevMonthButton.disabled = true;
        } else {
            prevMonthButton.classList.remove('disabled');
            prevMonthButton.disabled = false;
        }

        const maxMonth = today.getMonth() + 3;
        const maxYear = today.getFullYear() + Math.floor(maxMonth / 12);
        const adjustedMaxMonth = maxMonth % 12;

        if (currentYear > maxYear || (currentYear === maxYear && currentMonth > adjustedMaxMonth)) {
            nextMonthButton.classList.add('disabled');
            nextMonthButton.disabled = true;
        } else {
            nextMonthButton.classList.remove('disabled');
            nextMonthButton.disabled = false;
        }
    }

    function generateCalendar(month, year) {
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
                td.addEventListener('click', function() {
                    document.querySelectorAll('.calendar td.selected').forEach(selectedTd => {
                        selectedTd.classList.remove('selected');
                    });
                    this.classList.add('selected');
                    selectedDate = this.dataset.date; // 선택한 날짜 저장
                    updateButtons(); // 버튼 상태 업데이트
                });
            }
        });
    }

    function updateCalendar() {
        generateCalendar(currentMonth, currentYear);
        updateButtons();
    }

    prevMonthButton.addEventListener('click', function() {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        updateCalendar();
    });

    nextMonthButton.addEventListener('click', function() {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        updateCalendar();
    });

    updateCalendar(); // 초기 달력 생성

    // 초기 선택 날짜 설정: 오늘 날짜 선택
    document.querySelectorAll('.calendar td').forEach(td => {
        if (td.innerText == today.getDate() && currentMonth === today.getMonth() && currentYear === today.getFullYear()) {
            td.click(); // 오늘 날짜 클릭 이벤트 트리거
        }
    });
});
