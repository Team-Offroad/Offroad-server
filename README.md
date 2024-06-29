# 💻 Offroad-Server

---

🍀 NOW-SOPT 34th, Team "Offroad"의 Server Repository입니다.

# 📖 Project Name & Description

---

📛 Name : Offroad (오프로드)

📛️ Description : 일상을 벗어나, 잠시 모험을 떠날 수 있게 해주는 서비스입니다.

# ⌨️ Development Information

---

## Environment

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![aws](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)


## Tools
![git](	https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)


## Social
![slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![discord](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)
![notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)



## Stacks
![redis](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white)
![sprint security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)




# 🧙 Members & Roles

---

| 최윤한                                           | 김환준                                                                               | 김의진                                                                          |
|-----------------------------------------------|-----------------------------------------------------------------------------------|------------------------------------------------------------------------------|
| <img width="200px" height="200px" alt="image" src="https://github.com/Team-Offroad/Offroad-server/assets/127496156/9815c501-03ab-486e-8b57-95c957c3337f">        | <img width="200px" height="200px" alt="image" src="https://github.com/Team-Offroad/Offroad-server/assets/127496156/98bcc906-7b41-465c-9079-dc891d012490">                                            | <img width="200px" height="200px" alt="image" src="https://github.com/Team-Offroad/Offroad-server/assets/127496156/7133be13-0ec8-488f-b1f5-6cb3ee3c00c4">                                 |
| ❗️서버리드 <br> ❗서버 초기 세팅 <br> ❗Sentry 및 Test 세팅  | ❗ Discord Webhook 구축 <br> ❗Exception Handling <br> ❗API 명세서 템플릿 작성 <br> ❗DB 초안 설계 | ❗Docker기반 CI/CD 구축 <br> ❗️API 공통 response 작성 <br> ❗Swagger 세팅 <br> ❗소셜로그인 구현 |

# 🌊 Git Flow

<img width="818" alt="image" src="https://github.com/Team-Offroad/Offroad-server/assets/127496156/216a2d32-628a-4b8f-a4a1-ae06ef35a1fd">

# 📒 Convention

---

## ☕️ Code Convention

✅ 타입 : Pascal Case

✅ 변수 및 함수 : Camel Case

✅ 상수 : Snake Case

## ☕️ Commit Convention

| type     | description |
|----------|-----------|
| feat     | 기능개발      |
| refactor | 코드 개선     |
| chore    | 기타 사소한 수정 |
| fix      | 버그 수정 관련  |
| setting  | 세팅 관련     |
| test     | 테스트 관련    |
| modify   | 코드 수정이 생길 경우 |

```bash
[type] : content
```

## ☕️ Pull Request Convention

✅ 제목 : [작업 키워드] 작업내용 (ex : [feat] Docker 기반 CI/CD구축)

✅ 리뷰 : Pn룰 활용 (P3 제외)

✅ 템플릿, Label, Test 스크린샷 이용

# 🔧 Structure 

---

🌱 단일 모듈 프로젝트에서 멀티 모듈 프로젝트로 확장을 고려하여 구조를 생각했습니다.

```bash
📦site.offload.offloadserver
 ┣ 📂api
 ┃ ┣ 📂domainA
 ┃ ┃ ┣ 📜controller
 ┃ ┃ ┣ 📜service
 ┃ ┃ ┗ 📜usecase
 ┃ ┗ 📂domainB
 ┃ ┃ ┣ 📜controller
 ┃ ┃ ┣ 📜service
 ┃ ┃ ┗ 📜usecase
 ┣ 📂common
 ┃ ┣ 📂exception
 ┃ ┗ 📂util
 ┣ 📂db
 ┃ ┣ 📂domainA
 ┃ ┃ ┣ 📜entity
 ┃ ┃ ┗ 📜repository
 ┃ ┗ 📂domainB
 ┃ ┃ ┣ 📜entity
 ┃ ┃ ┗ 📜repository
 ┣ 📂enums
 ┃ ┣ 📂domainA
 ┃ ┃ ┣ 📜enumA
 ┃ ┃ ┗ 📜enumB
 ┃ ┗ 📂domainB
 ┃ ┃ ┣ 📜enumA
 ┃ ┃ ┗ 📜enumB
 ┗ 📂external
 ┃ ┣ 📂discord
 ┃ ┗ 📂oauth
 ┃ ┃ ┣ 📂apple
 ┃ ┃ ┗ 📂kakao
```