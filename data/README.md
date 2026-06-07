# 데이터 형식 (data schema)

이 시스템은 데이터를 **쉼표(,) 구분 텍스트 파일**로 저장합니다.
실제 운영에 쓰던 파일은 개인정보 형태의 더미라 repo에 포함하지 않았고, 대신 **형식 설명 + 익명화 샘플**(`*.sample.txt`)을 둡니다.

> 실행하려면 샘플을 프로젝트 루트에 실제 파일명으로 복사하세요.
> 예: `cp data/students.sample.txt students.txt` (코드가 작업 디렉토리 루트에서 읽습니다.)

---

## students.txt — 학생
`id,name,password,gender,phone,email,department,completedSemesters,studentId,enrollmentYear,leaveOfAbsenceSemesters,previousGrade`

| # | 필드 | 의미 | 타입 |
|---|---|---|---|
| 1 | id | 로그인 ID | str |
| 2 | name | 이름 | str |
| 3 | password | 비밀번호(※평문 — IMPROVEMENTS.md 참고) | str |
| 4 | gender | 성별 | str |
| 5 | phone | 전화번호 | str |
| 6 | email | 이메일 | str |
| 7 | department | 학과 | str |
| 8 | completedSemesters | 이수 학기 수 | int |
| 9 | studentId | 학번 | int |
| 10 | enrollmentYear | 입학년도 | int |
| 11 | leaveOfAbsenceSemesters | 휴학 학기 수 | int |
| 12 | previousGrade | 직전 성적(평점) | double |

## professors.txt — 교수
`id,name,password,gender,phone,email,department,professorNumber(int)`

## employees.txt — 직원
`id,name,password,gender,phone,email,college,employeeNumber(int)`

## courses.txt — 강의
`name,code,credits(int),professor,capacity(int),schedule`
예) `전자회로,010321,3,홍길동교수,30,월B수B`

## enrollment.txt — 수강신청
`studentId,courseName,courseCode`

## counsel.txt — 상담
`studentId,professor,semester,content,note`

## courseHistory.txt — 수강 이력
`studentId,courseCode,credits(int),grade(double),semester`
(파일 상단의 `#` 시작 줄은 주석)
