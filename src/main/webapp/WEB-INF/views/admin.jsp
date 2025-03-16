<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사주팔자 관리자</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { 
            margin-top: 50px; 
        }
        .table th, .table td {
            vertical-align: middle;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container">
        <h2 class="text-center mb-4">사주팔자 결과 관리</h2>
        
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">저장된 결과 목록</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>이름</th>
                                <th>생년월일</th>
                                <th>성별</th>
                                <th>양/음력</th>
                                <th>윤달여부</th>
                                <th>사주</th>
                                <th>나이</th>
                                <th>등록일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${results}" var="result">
                                <tr>
                                    <td>0x-${result.id}</td>
                                    <td>${result.name}</td>
                                    <td>${result.birthDate}</td>
                                    <td>${result.gender == 'M' ? '남성' : '여성'}</td>
                                    <td>${result.solarLunar}</td>
                                    <td>${result.intercalation}</td>
                                    <td>
                                        ${result.yearPillar} ${result.monthPillar} ${result.dayPillar} ${result.timePillar}
                                    </td>
                                    <td>${result.age}</td>
                                    <td>${result.createdAt}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>