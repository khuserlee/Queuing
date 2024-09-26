<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
function showSection(sectionId) {
    // 모든 섹션을 숨김
    document.querySelectorAll('.section').forEach(function(section) {
        section.style.display = 'none';
    });
    // 선택된 섹션만 보이게 함
    document.getElementById(sectionId).style.display = 'block';
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('updateProfileForm').addEventListener('submit', function(e) {
        e.preventDefault();
        var form = this;
        fetch(form.action, {
            method: 'PATCH',
            body: new FormData(form),
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        }).then(response => {
            if (response.ok) {
                alert('회원정보가 성공적으로 수정되었습니다.');
            } else {
                alert('회원정보 수정에 실패했습니다. 다시 시도해주세요.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
    });
});
</script>