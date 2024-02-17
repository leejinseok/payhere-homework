# 페이히어 백엔드 엔지니어 과제 (이진석)

## 🏠 Overview

**소감**

페이히어 백엔드 사전과제로 rest api를 개발하였습니다.
코틀린으로 작성하면 좋으나 아직 코틀린 숙련도가 높지 않기에 Java로 작성하였습니다.
프로젝트를 만들면서 스스로에게 좋은 배움의 기회가 된거같습니다 <br><br>

**요구사항 만족**

기술적인 부분도 중요하지만 제일 중요한건 요구사항에 맞게 올바르게 작동하는게 중요하다는 생각이 들었습니다.
그래서 기능 요구사항을 면밀히 검토하여 정상적으로 잘 작동하는 api를 만드는 것을 최우선으로 삼았습니다. <br><br>

**TDD**

테스트 코드를 작성하여 신뢰도있는 프로젝트를 만들고자 하였습니다.
발생할 수 있는 최대한 많은 경우에 대하여 테스트 코드를 작성하였습니다.
<br><br>

**가독성**

개인 프로젝트이지만 협업 프로젝트라고 여기고 변수명이나 메소드명, 클래스명등 명명 규칙을
가독성있고 이해하기 쉬운 이름을 붙이려고 노력하였습니다.
<br><br>

**인증 & 인가**

spring security를 적용하였고 jwt로 사용자를 인증하는 방식으로 개발하였습니다.
<br><br>

**멀티 모듈**

프로젝트 모듈을 core와 api로 나누어 느슨하게 결합되도록 의도하였습니다.
만약에 해당 프로젝트에 batch 같은 모듈이 추가되었다고 가정할때 해당 batch 프로젝트에서는 core에서 필요한 부분만 import해서 사용할 수 있습니다.
<br><br>

**예외 처리**

예외처리를 적절하게 하여 api에서 해당 예외에 맞는 응답을 줄 수 있도록 advice controller를 활용하였습니다.
NotFound, NoPermission 등 커스텀 예외 처리를 추가하여 핸들링 할 수 있도록 했습니다.
<br><br>

**데이터**

java진영의 ORM인 jpa를 사용하였습니다. DB는 요구사항에 맞게 mysql5.7를 사용하였습니다. <br><br>

**Docker**

도커를 활용할 수 있도록 Dockerfile, docker-compose.yml파일을 작성하였습니다.
이미지는 어플리케이션 실행에 용이한 방식으로 ubuntu:18.04 이미지에 java 등등 필요한 패키지들을 설치한 뒤에 제 개인 docker hub에 올려놓았고
그 이미지를 사용하였습니다

링크: https://hub.docker.com/repository/docker/sonaky47/payhere-homework/general

**테스트 지원**

swagger doc을 사용하여 api를 사용하기 쉽게 공개하였습니다.
어플리케이션을 구동시킨 이후 아래의 url로 접속하면 swagger를 사용할 수 있습니다.

swagger url: http://localhost:8080/swagger-ui/index.html

**기타**

로그아웃은 클라이언트 쪽에서 삭제하는 방식으로 예상하여 별도 api 작업을 하지 않았습니다.

## 🏛️ Structure

### Api (payhere-api)

**Application Layer**

Domain

- Auth
- Product

Test Cases

- 회원가입
- 로그인실패
- 상품조회_단건
- 상품조회_페이지
- 상품등록
- 상품수정
- 상품수정 실패 권한없음
- 상품삭제
- 상품삭제 실패 권한없음

**Presentation Layer**

Domain

- Auth
- Product

Test Cases

- 회원가입
- 회원가입 실패 잘못된 폰번호
- 회원가입 실패 패스워드 공백
- 로그인
- 상품조회
- 상품조회 단건
- 상품등록
- 상품수정
- 상품삭제
- 한글초성추출

### Core (payhere-core)
**DB**

- ShopOwner
- Product


## 🎢 Tech Stack

- java 17
- gradle 8.5
- spring boot 3.1.5
- mysql 5.7
- h2
- jpa
- junit5
- swagger
- lombok
- logback
- docker


## 🏃 Run Application

도커로 어플리케이션 실행

```shell
./start.sh
```

## 👷 TODO

- [x] 사장님 엔티티 생성
- [x] 사장님 회원 가입
- [x] 사장님 로그인
- [x] 사장님 로그아웃
- [x] 사장님 상품등록
- [x] 사장님 상품수정
- [x] 사장님 상품삭제
- [x] 상품 상세 보기
- [x] 상품 리스트 보기 (page)
- [x] 상품 검색 (이름기반)
    - 초성검색 가능
- [x] 로그인 하지 않은 사장님은 상품 api 접근 불가
- [x] swagger 작성
- [x] 도커 생성
- [x] 도커로 실행
