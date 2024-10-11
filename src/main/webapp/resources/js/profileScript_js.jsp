<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 초기 페이지 로드 시 기본 섹션 표시
    showSection('reservation');

    // 네비게이션 링크에 이벤트 리스너 추가
    document.querySelectorAll('.sidebar a').forEach(function(link) {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            var sectionId = this.getAttribute('onclick').match(/'([^']+)'/)[1];
            showSection(sectionId);
        });
    });

	return;
	
    // 폼 제출 이벤트 리스너
    var updateProfileForm = document.getElementById('updateProfileForm');
    if (updateProfileForm) {
        updateProfileForm.addEventListener('submit', function(e) {
            e.preventDefault();
            var form = this;
            var formData = new FormData(form);

            // PATCH 메소드를 시뮬레이션하기 위해 POST로 보내고 _method를 PATCH로 설정
            formData.append('_method', 'PATCH');

            fetch(form.action, {
                method: 'POST',
                body: formData,
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            }).then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Network response was not ok.');
            }).then(data => {
                alert('회원정보가 성공적으로 수정되었습니다.');
                // 필요하다면 여기서 페이지를 새로고침하거나 업데이트된 정보를 화면에 반영
            }).catch(error => {
                console.error('Error:', error);
                alert('회원정보 수정에 실패했습니다. 다시 시도해주세요.');
            });
        });
    } else {
        console.error('Update profile form not found');
    }
});

function showSection(sectionId) {
    console.log('Showing section:', sectionId); // 디버깅을 위한 로그
    // 모든 섹션을 숨김
    document.querySelectorAll('.section').forEach(function(section) {
        section.style.display = 'none';
    });
    // 선택된 섹션만 보이게 함
    var selectedSection = document.getElementById(sectionId);
    if (selectedSection) {
        selectedSection.style.display = 'block';
    } else {
        console.error('Section not found:', sectionId);
    }
}
</script>