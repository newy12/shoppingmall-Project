
## 프로젝트

쇼핑몰 프로젝트


## 개발목표

- 지난 1년간 실무 경험을 토대로 사용하는 기술에 대한 복습 및 새로운 기술의 접목
- CI/CD 파이프라인 구축 및 자동빌드
- Docker를 활용한 이미지 컨테이너 호스트 및 관리  
- 상태코드에 따른 예외처리

## 진행순서
- 1.프로젝트 방향성 및 개발목표 수립
- 2.spring boot maven 프로젝트 intellij 개발 환경 구축
- 3.mysql 서버 구축
- 4.개발
- 5.CI/CD 파이프라인 구축(docker container 및 jenkins 자동 배포 및 slack 알람

## 기술스택
- OS
<img src="https://img.shields.io/badge/mac OS-000000?style=for-the-badge&logo=mac&logoColor=white">
- Framework
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
- Template engine
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> 
- Programming Language
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=JAVA&logoColor=white"> 
- Persistence Framework 
    <img src="https://img.shields.io/badge/Jpa-007396?style=for-the-badge&logo=Jpa&logoColor=white"    
- IDE
 <img src="https://img.shields.io/badge/intellj IDEA-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white">
- CI/CD
 <img src="https://img.shields.io/badge/DOCKER-2496ED?style=for-the-badge&logo=Docker&logoColor=white">  <img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">
 - Collaboration & tools
 <img src="https://img.shields.io/badge/GIT-D24939?style=for-the-badge&logo=GIT&logoColor=white"> <img src="https://img.shields.io/badge/GITHUB-181717?style=for-the-badge&logo=GITHUB&logoColor=white"> <img src="https://img.shields.io/badge/SLACK-4A154B?style=for-the-badge&logo=SLACK&logoColor=white">
<img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/ngrok-1F1E37?style=for-the-badge&logo=ngrok&logoColor=white"> 
- DataBase
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/h2-1F1E37?style=for-the-badge&logo=h2&logoColor=white">

## 개발

화면
- Thymeleaf-layout-dialect 이용한 중복된 레이아웃 설계
- Bootstrap 이용한 화면 디자인 설계

화면구성

 사용자 화면                                            
 - 메인페이지                                        
- 마이페이지
- 장바구니페이지
- 옷 종류별페이지
- 상세페이지
- 결제페이지
- 로그인페이지
- 주문페이지

관리자 화면
- 주문페이지
- 사용자목록페이지
- 주문정보목록페이지

기타 화면
- 404 에러페이지



로그인
- 스프링 시큐리티 로그인 인증
- 스프링 시큐리티 활용 password 암호화 db저장

소셜로그인
- 카카오 로그인 API 인증 및 허가

비밀번호 초기화
- SMTP 프로토컬 활용 및 이메일 전송

회원가입
- 필수입력 사항 체크
- 중복 회원 체크
- 각 항목 유효성 체크

기타
- error 핸들러를 통한 404페이지 커스터마이징
- 아임포트API 활용 및 결제(KG이니시스)

## 배포

- Docker 환경에 Jenkins 이미지 컨테이너 구축
- Jenkins 와 Github Repository 연동
- ngrok을 이용한 외부망 localhost 접근 구축
- WebHook을 이용하여 Repository push 이벤트 발생시 Jenkins 자동 빌드
- Jenkins 빌드 성공 및 실패 시 slack을 통한 알림 메세지 발송
 
 ## 프로젝트 정리 velog

- [Docker와 Jenkins로 자동빌드 및 Slack 알림 뱃지 발송(1)](https://yjkim-dev.tistory.com/11)
- [Docker와 Jenkins로 자동빌드 및 Slack 알림 뱃지 발송(2)](https://yjkim-dev.tistory.com/12) 
- [Docker와 Jenkins로 자동빌드 및 Slack 알림 뱃지 발송(3)](https://yjkim-dev.tistory.com/13)
- [Docker와 Jenkins로 자동빌드 및 Slack 알림 뱃지 발송(4)](https://yjkim-dev.tistory.com/14)
- [Docker와 Jenkins로 자동빌드 및 Slack 알림 뱃지 발송(5)](https://yjkim-dev.tistory.com/15)
- [ngrok 이용한 외부망에서 localhost 접속 구축](https://yjkim-dev.tistory.com/16)


## ERD
![image](https://user-images.githubusercontent.com/73875312/149170340-6f469c3d-02fd-4e64-8e75-397abc33a1a8.png)








