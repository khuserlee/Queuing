<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>예약 수정 실패 페이지</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 20px;
            background-color: #f8d7da; /* 부드러운 빨간색 배경 */
        }

        .error-message {
            font-size: 24px;
            font-weight: bold;
            color: #721c24; /* 어두운 빨간색 */
            margin-top: 20px;
        }

        .details {
            margin-top: 20px;
            font-size: 18px;
            color: #856404; /* 어두운 노란색 */
        }

        .button-container {
            margin-top: 30px;
        }

        .button-container input {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="title">
        <h3>큐잉(Queuing) - 식당 예약/웨이팅 웹 서비스</h3>
    </div>

    <div class="error-message">예약 수정이 실패했습니다!</div>

    <div class="details">
        <p>죄송합니다, 고객님의 예약 수정이 처리되지 않았습니다.</p>
        <p>Error 메시지: ${errorMessage}</p> <!-- 서버에서 전달된 오류 메시지 -->
    </div>

    <div class="button-container">
        <input type="button" value="메인 페이지로 돌아가기" onclick="location.href='index.html';" />
        <input type="button" value="예약 목록으로 돌아가기" onclick="location.href='reservations.html';" /> <!-- 예약 목록으로 돌아가는 버튼 추가 -->
    </div>
</body>
</html>
