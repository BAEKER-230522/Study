<div align="center"><h1>
    🤚🏻BAEKER 의 Study Server 입니다.
</h1></div>

<div align="center"><h3>
    스터디 정보 관리, 미션 생성과 결과 관리를 담당합니다.
</h3></div>

<div align="center"><a href="https://github.com/BAEKER-230522"><b>
    🔗 Organization page
</b></a></div>
<div align="center"><a href="https://github.com/BAEKER-230522/Gateway"><b>
    🔗 Gateway Server
</b></a></div>

<br>

## ERD
### Study
- STUDY : 스터디 정보를 관리
- MY_STUDY : 스터디에 가입한 회원 정보를 관리
  - Enum 객체인 STUDY_STATUS 를 사용한 상태 관리
- STUDY_SMAPSHOT : 스터디에서 해결한 7일간의 미션 내역 관리
  - BASE_ENTITY 를 사용한 STUDY 와 중복 필드 제거

### Mission
- PROBLEM : 백준 문제 관리
- PROBLEM_STATUS : 스터디 미션 관리
- PERSONAL_STUDY_RULE : 스터디원의 미션 해결 현황

<img width="964" alt="스크린샷 2023-10-13 오후 2 00 34" src="https://github.com/BAEKER-230522/Community/assets/115536240/24ce2328-d795-4cf3-8991-dd6e1b573404">

<br><br>

## 핵심 요구사항
## 1. Study
### 01 생성
- 스터디 생성
- 스터디 가입 요청
- 회원에게 스터디로 초대 요청

<br>

### 02 조회
- id 로 스터디 조회
- 이름으로 스터디 조회
- 모든 스터디 목록 조회 + 페이징
- 7일간 스터디에서 해결한 문제수 조회
- 회원이 가입중인 스터디 목록 조회
- 스터디의 정회원 목록 조회
- 스터디 가입 요청중, 초대중인 회원 목록 조회
- 랭킹순으로 스터디 목록 조회
- 검색어로 스터디 목록 조회

<br>

### 03 수정
- 스터디 정보 수정
- 스터디장 위임
- 스터디 경험치 상승
- 스터디에서 해결한 문제수 최신화
- 초대, 가입 요청 메시지 수정
- 정회원으로 등급 수정


<br>

### 04 삭제
- 스터디 삭제
- 가입, 초대 거절
- 스터디 탈퇴
- 스터디원 강제 추방

<br>

## 2. Mission
### 01 생성
- 

<br>

### 02 조회
- 

<br>

### 03 수정
- 

<br>

### 04 삭제
- 

<br>

