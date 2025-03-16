<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사주팔자 데모</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500;700&family=Noto+Serif+KR:wght@400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #f8f9fc;
            --accent-color: #e74a3b;
            --text-color: #5a5c69;
            --border-color: #e3e6f0;
            --success-color: #1cc88a;
        }
        
        body {
            font-family: 'Noto Sans KR', sans-serif;
            color: var(--text-color);
            background-color: #f8f9fc;
            background-image: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
            min-height: 100vh;
        }
        
        .container { 
            max-width: 700px; 
            margin-top: 50px; 
            margin-bottom: 50px;
        }
        
        h2, h4, h5 {
            font-family: 'Noto Serif KR', serif;
            color: #2e3951;
        }
        
        .form-group { 
            margin-bottom: 1.5rem; 
        }
        
        .form-control, .btn {
            border-radius: 10px;
            padding: 0.6rem 1rem;
        }
        
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.2rem rgba(78, 115, 223, 0.25);
        }
        
        .form-check-input:checked {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            font-weight: 500;
            letter-spacing: 0.5px;
            padding: 0.7rem 1.5rem;
            transition: all 0.3s;
        }
        
        .btn-primary:hover {
            background-color: #3a5ccc;
            border-color: #3a5ccc;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(78, 115, 223, 0.3);
        }
        
        .result-box {
            margin-top: 2rem;
            padding: 1rem;
            border: none;
            border-radius: 15px;
            display: none;
            background-color: white;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15);
        }
        
        .fortune-table {
            margin-top: 20px;
            padding: 15px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.1);
        }
        
        .fortune-char {
            font-family: "SimSun", "NSimSun", serif;
            line-height: 1.6;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            transition: all 0.3s;
        }
        
        .fortune-char:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        .saju-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.1);
        }
        
        .saju-table th, .saju-table td {
            border: 1px solid var(--border-color);
            padding: 8px;
            text-align: center;
        }
        
        .saju-table th {
            background-color: var(--primary-color);
            color: white;
            font-weight: 500;
            padding: 12px 8px;
        }
        
        .hanja {
            font-family: "SimSun", "NSimSun", serif;
            font-size: 1.4em;
            line-height: 1.5;
            display: block;
            margin-top: 5px;
            color: #2c3e50;
        }
        
        .saju-table td {
            height: 80px;
            vertical-align: middle;
            padding: 10px 8px;
            transition: all 0.3s;
        }
        
        .saju-table tr:nth-child(even) {
            background-color: rgba(78, 115, 223, 0.05);
        }
        
        .saju-table td:first-child {
            background-color: rgba(78, 115, 223, 0.1);
            font-weight: 500;
        }
        
        .alert-success {
            background-color: rgba(28, 200, 138, 0.1);
            border-color: rgba(28, 200, 138, 0.2);
            color: var(--success-color);
            border-radius: 10px;
        }
        
        .alert-warning {
            background-color: rgba(246, 194, 62, 0.1);
            border-color: rgba(246, 194, 62, 0.2);
            color: #f6c23e;
            border-radius: 10px;
        }
        
        /* 애니메이션 효과 */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        
        #resultBox {
            animation: fadeIn 0.5s ease-out;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="text-center mb-4"><i class="bi bi-stars"></i> 사주팔자 확인하기</h2>
        <form id="saJuForm" class="bg-white p-5 rounded-3 shadow">
            <div class="form-group">
                <label for="name" class="form-label"><i class="bi bi-person"></i> 이름</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="birthDate" class="form-label"><i class="bi bi-calendar-event"></i> 생년월일</label>
                <input type="date" class="form-control" id="birthDate" name="birthDate" required>
            </div>
            <div class="form-group">
                <label for="birthTime" class="form-label"><i class="bi bi-clock"></i> 태어난 시간</label>
                <input type="time" class="form-control" id="birthTime" name="birthTime" required>
            </div>
            <div class="form-group">
                <label class="form-label"><i class="bi bi-gender-ambiguous"></i> 성별</label>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="male" name="gender" value="M" required>
                    <label class="form-check-label" for="male">남성</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="female" name="gender" value="F" required>
                    <label class="form-check-label" for="female">여성</label>
                </div>
            </div>
            <div class="form-group">
                <label class="form-label"><i class="bi bi-moon-stars"></i> 양력/음력</label>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="solar" name="solarLunar" value="양력" checked>
                    <label class="form-check-label" for="solar">양력</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="lunar" name="solarLunar" value="음력">
                    <label class="form-check-label" for="lunar">음력</label>
                </div>
            </div>
            <div class="form-group">
                <label class="form-label"><i class="bi bi-calendar-plus"></i> 윤달 여부</label>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="regular" name="intercalation" value="평달" checked>
                    <label class="form-check-label" for="regular">평달</label>
                </div>
                <div class="form-check">
                    <input type="radio" class="form-check-input" id="leap" name="intercalation" value="윤달">
                    <label class="form-check-label" for="leap">윤달</label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary w-100 mt-3"><i class="bi bi-search"></i> 사주팔자 보기</button>
        </form>
        
        <div id="resultBox" class="result-box">
            <h4 class="text-center mb-4"><i class="bi bi-stars"></i> 사주팔자 결과</h4>
            <div id="sajuResult"></div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#saJuForm').on('submit', function(e) {
                e.preventDefault();
                
                // 날짜와 시간 형식 확인
                const birthDate = $('#birthDate').val();
                const birthTime = $('#birthTime').val();
                
                console.log('전송 데이터 확인:');
                console.log('이름:', $('#name').val());
                console.log('생년월일:', birthDate);
                console.log('시간:', birthTime);
                console.log('성별:', $('input[name="gender"]:checked').val());
                
                $.ajax({
                    url: '/api/saju/calculate',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        name: $('#name').val(),
                        birthDate: birthDate,
                        birthTime: birthTime,
                        gender: $('input[name="gender"]:checked').val(),
                        solarLunar: $('input[name="solarLunar"]:checked').val(),
                        intercalation: $('input[name="intercalation"]:checked').val()
                    }),
                    success: function(response) {
                        console.log('Response 전체:', response);
                        
                        // 응답 객체가 문자열인 경우 파싱
                        if (typeof response === 'string') {
                            try {
                                response = JSON.parse(response);
                                console.log('파싱된 응답:', response);
                            } catch (e) {
                                console.error('JSON 파싱 오류:', e);
                            }
                        }
                        
                        // 입력한 날짜 정보 파싱
                        let inputDate = new Date(birthDate);
                        let inputMonth = inputDate.getMonth() + 1; // getMonth()는 0부터 시작하므로 +1
                        let inputDay = inputDate.getDate();
                        let inputTime = birthTime.split(':')[0] + birthTime.split(':')[1];
                        
                        // 응답 객체 디버깅
                        console.log('이름:', response.name);
                        console.log('년도:', response.year);
                        console.log('년간:', response.yearStem);
                        console.log('년지:', response.yearBranch);
                        console.log('월:', response.month);
                        console.log('일:', response.day);
                        console.log('시:', response.time);
                        
                        // 안전하게 속성 접근하는 함수
                        function safeGet(obj, prop, defaultValue) {
                            return (obj && obj[prop] !== undefined && obj[prop] !== null) ? obj[prop] : (defaultValue || '');
                        }
                        
                        // 사주 테이블 생성
                        let sajuTable = '<table class="saju-table">' +
                            '<tr>' +
                                '<th>사주 원국</th>' +
                                '<th>시</th>' +
                                '<th>일</th>' +
                                '<th>월</th>' +
                                '<th>년</th>' +
                            '</tr>' +
                            '<tr>' +
                                '<td>천간</td>' +
                                '<td>' + safeGet(response, 'timeStem') + '<br><span class="hanja">' + safeGet(response, 'timeStemHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'dayStem') + '<br><span class="hanja">' + safeGet(response, 'dayStemHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'monthStem') + '<br><span class="hanja">' + safeGet(response, 'monthStemHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'yearStem') + '<br><span class="hanja">' + safeGet(response, 'yearStemHanja') + '</span></td>' +
                            '</tr>' +
                            '<tr>' +
                                '<td>지지</td>' +
                                '<td>' + safeGet(response, 'timeBranch') + '<br><span class="hanja">' + safeGet(response, 'timeBranchHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'dayBranch') + '<br><span class="hanja">' + safeGet(response, 'dayBranchHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'monthBranch') + '<br><span class="hanja">' + safeGet(response, 'monthBranchHanja') + '</span></td>' +
                                '<td>' + safeGet(response, 'yearBranch') + '<br><span class="hanja">' + safeGet(response, 'yearBranchHanja') + '</span></td>' +
                            '</tr>' +
                            '<tr>' +
                                '<td></td>' +
                                '<td>시(' + safeGet(response, 'time', inputTime) + ')</td>' +
                                '<td>일(' + safeGet(response, 'day', inputDay) + ')</td>' +
                                '<td>월(' + safeGet(response, 'month', inputMonth) + ')</td>' +
                                '<td>년(' + safeGet(response, 'year', inputDate.getFullYear()) + ')</td>' +
                            '</tr>' +
                        '</table>';
                        
                        // 대운수 표시 부분 수정
                        let fortuneTable = '<h5 class="mb-3">대운수 (' + (response.majorFortuneDirection || '') + ')</h5>';
                        
                        if (response.majorFortunes) {
                            // 시작 나이 설정 (서버에서 계산된 값 사용)
                            let startAge = response.majorFortuneStartAge || 3;
                            
                            // 대운수 개수 설정 (최소 12개 표시, 110세까지 표시)
                            let fortuneCount = 12;
                            
                            // 나이 행
                            fortuneTable += '<div class="d-flex justify-content-around mb-3">';
                            for(let i = 0; i < fortuneCount; i++) {
                                let age = startAge + (i * 10);
                                fortuneTable += '<div class="text-center" style="width: 60px">' + age + '</div>';
                            }
                            fortuneTable += '</div>';
                            
                            // 한자 행 (천간과 지지 함께 표시)
                            fortuneTable += '<div class="d-flex justify-content-around mb-3">';
                            for(let i = 0; i < fortuneCount; i++) {
                                let stemChar = '';
                                let branchChar = '';
                                
                                // 천간 한자 (대운수의 천간) - 안전하게 접근
                                if (response.majorFortuneChars && i < response.majorFortuneChars.length) {
                                    stemChar = response.majorFortuneChars[i];
                                }
                                
                                // 지지 한자 (대운수의 지지) - 안전하게 접근
                                if (response.majorFortuneBranchChars && i < response.majorFortuneBranchChars.length) {
                                    branchChar = response.majorFortuneBranchChars[i];
                                }
                                
                                fortuneTable += '<div class="fortune-char text-center" style="width: 60px; height: 80px; font-size: 22px; border: 1px solid #ddd; padding: 8px 5px; background-color: #f8f9fa">' + 
                                       stemChar + '<br>' + branchChar + '</div>';
                            }
                            fortuneTable += '</div>';
                            
                            // 한글 행 (천간과 지지 함께 표시)
                            fortuneTable += '<div class="d-flex justify-content-around">';
                            for(let i = 0; i < fortuneCount; i++) {
                                let stem = '';
                                let branch = '';
                                
                                // 천간 한글 (대운수의 천간) - 안전하게 접근
                                if (response.majorFortunes && i < response.majorFortunes.length) {
                                    stem = response.majorFortunes[i];
                                }
                                
                                // 지지 한글 (대운수의 지지) - 안전하게 접근
                                if (response.majorFortuneBranches && i < response.majorFortuneBranches.length) {
                                    branch = response.majorFortuneBranches[i];
                                }
                                
                                fortuneTable += '<div class="text-center" style="width: 60px">' + stem + branch + '</div>';
                            }
                            fortuneTable += '</div>';
                            
                            // 시작 나이 정보 추가
                            fortuneTable += '<div class="mt-3 text-muted small">* 대운수는 ' + startAge + '세부터 시작합니다.</div>';
                        } else {
                            fortuneTable += '<div class="alert alert-warning">대운수 정보가 없습니다.</div>';
                        }
                        
                        // 결과 표시
                        let html = '<div class="saju-result">' +
                            '<p class="mb-4 text-center fs-5"><strong>' + safeGet(response, 'name') + '</strong>님의 사주팔자</p>' +
                            '<div class="alert alert-success p-3"><i class="bi bi-check-circle-fill me-2"></i>결과가 데이터베이스에 저장되었습니다.</div>' +
                            sajuTable +
                            '<div class="mt-5">' + fortuneTable + '</div>' +
                            '</div>';
                        
                        $('#sajuResult').html(html);
                        $('#resultBox').show();
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', xhr, status, error);
                        console.error('응답 텍스트:', xhr.responseText);
                        alert('계산 중 오류가 발생했습니다.');
                    }
                });
            });
        });
    </script>
</body>
</html>