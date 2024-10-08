<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<!DOCTYPE html><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>

    <title>메뉴 목록</title>

</head>

<body>

    <h1>메뉴 목록</h1>

    <form  method="post">

        <table>

            <tr>

                <th>사진</th>

                <th>이름</th>

                <th>가격</th>

                <th>상세정보</th>

                <th>선택</th>

            </tr>

            <c:forEach var="menu" items="${menuList}">

                <tr>

                	<td><img src="" alt="Menu Image" /></td>
                    <td>${menu.name}</td>

                    <td>${menu.price}</td>

                    <td>${menu.description}</td>

                    <td><input type="checkbox" name="selectedMenuId" value="${menu.menuId}" /></td>

                </tr>

            </c:forEach>

        </table>

       <button type="submit" formaction="<c:url value='/menu/updateView' />">메뉴 수정</button>

        <button type="submit" formaction="<c:url value='/menu/delete' />">메뉴 삭제</button>

        <button type="submit" formaction="<c:url value='/menu/registerView' />">메뉴 등록</button>

    </form> 


<script>

    function validateSelection(action) {

        var checkboxes = document.querySelectorAll('input[name="selectedMenuIds"]:checked');

        if (action === 'register') {

            // 메뉴 등록 버튼 클릭 시 체크박스 확인 필요 없음

            return true;

        } else {

            if (checkboxes.length === 1) {

                return true;

            } else {

                alert('메뉴 수정 또는 삭제를 위해서는 정확히 하나의 메뉴를 선택해야 합니다.');

                return false;

            }

        }

    }

​// ------------------------------------------------------------------------------

    document.querySelector('form').onsubmit = function() {

        var action = document.querySelector('button[name="action"]').value;

        return validateSelection(action);
      };
// ----------------------------------------------------------------------------


    validateSelection(action);
      

</script>
</body>
</html>