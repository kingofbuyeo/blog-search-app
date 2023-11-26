# 오픈 API를 활용한 검색 어플리케이션

[Execute Jar 파일 다운로드 Link](https://github.com/kingofbuyeo/blog-search-app/blob/master/blog-search-app.jar)

# 1. 모듈 구조

## 1.1 blgo-search-app

1. **application**
    - 어플리케이션 구동을 위한 파일 및 단위 테스트 케이스를 하기 위한 모듈
    - 해당 모듈의 kotlin/com/yongchul 패키지 안에 단위 테스트 코드 존재
2. **config**
    - 어플리케이션 구성을 위한 환경 설정 파일 및 클래스 모듈
3. **core**
    - 어플리케이션의 핵심 비즈니스 도메임 모델 및 도메인 서비스 모듈
4. **gateway**
    - 외부 서비스 및 미들웨어 연동과 외부에 제공되는 어플리케이션 API를 제공하기 위한 모듈
5. **integration-test**
    - 어플리케이션에서 제공되는 핵심 비즈니스 API의 통합 테스트를 수행하기 위한 모듈
6. **usecase**
    - 비즈니스 도메인의 요구사항을 수행하기 위한 도메인들 간의 오케이스트레이션 수행을 위한 모듈
7. 모듈 의존성

   ![https://github.com/kingofbuyeo/blog-search-app/blob/master/blog-search-app-module-dependency.png?raw=true](https://github.com/kingofbuyeo/blog-search-app/blob/master/blog-search-app-module-dependency.png?raw=true)


# 2. 활용 라이브러리

- `org.jetbrains.kotlin:kotlin-reflect`
    - 코틀린 리플렉션 기능을 사용하기 위한 라이브러리
- `org.jetbrains.kotlin:kotlin-stdlib-jdk8`
    - jdk8이하 버전을 코틀린과 호환할 수 있는 표준 라이브러리 제공
- `io.kotest:kotest-runner-junit5`
- `io.kotest:kotest-assertions-core`
- `io.kotest:kotest-property`
    - 통합 테스트(또는 테스트)를 위한 Kotest 라이브러리
- `org.springframework.boot:spring-boot-properties-migrator`
    - SpringBoot3버전 이하에서 사용했던 기능들이 사용된 경우 SpringBoot3에서 사용 될 수 있도록 추가
- `io.netty:netty-resolver-dns-native-macos:4.1.79.Final:osx-aarch_64`
    - m1칩을 사용하는 맥을 위한 라이브러리
- `org.jetbrains.kotlinx:kotlinx-serialization-json`
    - Kotlin 직렬화 및 역직렬화를 위한 라이브러리

---

---

# 블로그 검색 어플리케이션

# 1. 공통사항

## 1.1 호스트 주소

1. Executor jar 사용한경우 http://localhost:8080으로 host 주소로 사용함

## 1.2 응답 규격

| HTTP Status | 200 (성공시)
400 (파라미터 오류)
500 (서버 오류) |
| --- | --- |

# 2. API 리스트

## 2.1 블로그 검색 API

- 블로그 검색 API는 최초 검색시 Kakao 블로그 검색 Open API를 사용하며, 해당 API의 장애 발생시 Naver 검색 API를 사용하여 검색을 수행

| Method | URL |
| --- | --- |
| GET | http://localhost:8080/v1/search/blog |
- 요청 파라미터

| 이름 | 타입 | 설명 | 필수여부 |
| --- | --- | --- | --- |
| query | String | Kakao 검색 수행될 경우 블로그 URL + “ “(공백)을 구분자로 넣어서 특정 블로그만 검색 수행가능 | O |
| page | Number | 기본적으로 1~50의 값을 제공하고 50이상인경우 Naver 블로그 검색이 수행됨. | X |
| pageSize | Number | 한 페이지에 보여질 문서의 수 1~50 사이의 값, 기본 50 | X |
| ordering | String | 결과 문서 정렬 방식, 기본값 ACCURACY
RECENT : 최신순 정렬
ACCURACY : 검색 정확도순 정렬 | X |
- Response

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| totalCount | Number | 검색된 전체 문서의 수 |
| hasNext | Boolean | Pagination을 위한 다음 페이지 존재 여부 |
| items | BlogInfo | 검색된 블로그 문서의 정보 |
- BlogInfo

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| title | String | 블로그 글 제목 |
| contents | String | 블로그 글 요약 |
| link | String | 블로그 글 URL |
| blogName | String | 블로그의 이름 |
| createdAt | DateTime | 블로그 게시 날짜 정보 |
- 응답 예제

```json
{
    "totalCount": 11702,
    "hasNext": true,
    "items": [
        {
            "title": "토스",
            "contents": "토스 기업정보 https://toss.im/ 토스 금융의 모든 것, 토스에서 쉽고 간편하게 toss.im *나무위키 - 기본 정보 https://namu.wiki/w/%ED%86%A0%<b>EC</b>%8A%A4(%<b>EA</b>%<b>B</b><b>8%</b><b>88%</b><b>EC</b>%<b>9C</b>%<b>B5</b> 토스(금융) - 나무위키 2022년 02월 09일 기준 학생학자금 이자 지원받기숨은 장학금 찾기학자금대출 계산기국가장학금 계산기사장님내 매출...",
            "link": "https://connect-thedots.tistory.com/32",
            "blogName": "비즈니스 모델 아카이빙",
            "createdAt": "2023-09-18T12:08:04"
        },
        {
            "title": "[주식 공부] 콜옵션, 풋옵션 쉽게 이해하기",
            "contents": "이득) * 더 자세한 설명과 이해는 관련 링크 참조 이런 정보는 어떠신가요? ⏬⏬⏬ 관련 링크 [나무위키 - 옵션(금융)] https://namu.wiki/w/%<b>EC</b>%98%<b>B</b>5%<b>EC</b>%85%98(%<b>EA</b>%<b>B</b><b>8%</b><b>88%</b><b>EC</b>%<b>9C</b>%<b>B5</b>)#s-4.1.1 옵션(금융) - 나무위키 만기시점에서 옵션의 가치는 다음과 같다. 콜옵션(조기상환권) 매수: Max{0, S - K}풋옵션(조기상환청구권...",
            "link": "https://invest-in-best.tistory.com/11",
            "blogName": "INVEST in BEST",
            "createdAt": "2023-07-29T17:37:18"
        }
    ]
}
```

---

## 2.2 인기 검색어 API

- 인기검색어 API는 검색한 키워드 중 가장 많이 검색된 키워드 기반으로 상위 10개의 검색어를 제공합니다.

| Method | URL |
| --- | --- |
| GET | http://localhost:8080/v1/search/keywords |
- 요청파라미터 없음
- Response

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| hasNext | Boolean | Pagination을 위한 다음 페이지 존재 여부 |
| items | PopularKeywordInfo | 인기 검색어 관련 정보 |
- PopularKeywordInfo

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| keyword | String | 사용자들이 검색한 단어 |
| count | Number | 해당 단어가 검색된 횟수 |
- 응답 예제

```json
{
    "hasNext": false,
    "items": [
        {
            "keyword": "카카오",
            "count": 6
        },
        {
            "keyword": "금융",
            "count": 5
        },
        {
            "keyword": "영화",
            "count": 4
        },
        {
            "keyword": "꽃",
            "count": 3
        },
        {
            "keyword": "면접",
            "count": 3
        }
    ]
}
```

---