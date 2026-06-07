# 🎓 학사관리 시스템 (University Management System)

> **아주대학교 전자공학프로그래밍1 전공 프로젝트** · Java(Swing) GUI 데스크톱 애플리케이션
>
> 로그인·권한별 화면(학생/교수/직원)·수강신청·성적/정보 조회·상담을 제공하는 학사 관리 시스템입니다. 데이터는 **파일(txt) 기반**으로 저장·로드합니다.

---

## 1. 개요

학생·교수·직원이 각자의 권한에 맞는 학사 서비스를 이용하는 GUI 프로그램입니다. 프로그램 시작 시 파일에서 데이터를 로드하고, 종료 시 파일로 저장합니다.

- **학생**: 수강신청 / 수강 내역 조회 / 상담 신청 / 성적 조회
- **교수**: 담당 강의 조회 / 학생 성적 입력 / 상담 관리
- **직원(관리자)**: 전체 학생·강의 정보 조회 / 수강신청 기간 설정 / 정보 편집

---

## 2. 사용 기술

| 영역 | 기술 |
|---|---|
| 언어 | **Java 17** |
| GUI | **Java Swing** (`JFrame`/`JPanel`/`JButton` 등, 8개 화면 클래스) |
| 데이터 | **파일 I/O** (`BufferedReader`/`BufferedWriter`, CSV 형식 txt) |
| 동시성 | **`Thread` + `synchronized`** — 수강신청 추가/삭제 시 공유 자원 락으로 **동시 접근 충돌 방지** ([`Enrollment.java`](src/unimanagement/Enrollment.java)) |
| 구조 | 상속 기반 권한 분리 — `Management`(기반·데이터 로딩) → `LogIn` → `Logged_Student`/`Logged_Professor`/`Logged_Employee` |

---

## 3. 내 역할 / 정직 범위

- **개인 프로젝트** — 설계·구현 전반(16개 클래스, 약 2,800줄) 직접 작성.
- ⚠️ **정직 라벨 (개선 요소)**: 당초 **MySQL 연동을 목표**로 잡고 커넥터(`mysql-connector-j`)까지 준비했으나, 설치·구동 단계에서 막혀 **최종 구현은 파일(txt) 기반**까지였습니다. (코드에 JDBC는 없으며, 커넥터 jar도 빌드 패스에 연결되지 않은 채 남아 있었습니다.) → 이 한계와 개선 계획은 [`IMPROVEMENTS.md`](IMPROVEMENTS.md)에 정리했습니다.

---

## 4. 폴더 구조

```
unimanagement/
├── src/unimanagement/      16개 .java (Swing GUI + 파일 I/O + 권한별 로직)
│   ├── Management.java        ← main 진입점, 데이터 로드/저장
│   ├── LogIn.java             로그인 화면
│   ├── Logged_Student.java    학생 화면
│   ├── Logged_Professor.java  교수 화면
│   ├── Logged_Employee.java   직원 화면
│   ├── Enrollment.java        수강신청 (Thread + synchronized)
│   └── ... (Student/Professor/Course/Member 등 도메인 클래스)
├── data/                   데이터 형식 설명 + 익명화 샘플
│   ├── README.md
│   └── *.sample.txt
├── docs/                   설계 보고서(PDF)
├── IMPROVEMENTS.md         개선 방향 (MySQL 마이그레이션 등)
├── .classpath / .project   Eclipse 프로젝트 설정
└── .gitignore
```

---

## 5. 실행 방법

> ⚠️ 실제 운영 데이터(`students.txt` 등)는 개인정보 형태의 더미라 **repo에 포함하지 않았습니다.**
> 데이터 형식과 익명화 샘플은 [`data/`](data/) 폴더를 참고하세요.

```bash
# 1) 데이터 준비: data/ 의 샘플을 프로젝트 루트에 실제 파일명으로 배치
#    (코드가 작업 디렉토리 루트에서 students.txt, courses.txt ... 를 읽습니다)
cp data/students.sample.txt students.txt   # courses, professors, employees 등 동일

# 2) 컴파일 & 실행 (Java 17)
javac -encoding UTF-8 -d bin src/unimanagement/*.java
java -cp bin unimanagement.Management
```

또는 **Eclipse**에서 `File > Import > Existing Projects into Workspace`로 폴더를 import → `Management.java` 실행.

---

## 6. 회고
- 파일 기반의 한계(동시성·무결성·검색 효율)를 RDB로 옮기는 것이 자연스러운 다음 단계 → [`IMPROVEMENTS.md`](IMPROVEMENTS.md).
- 권한별 화면을 상속으로 분리한 점은 유지하되, 데이터 접근부를 분리(DAO)하면 저장소 교체가 쉬워짐.
