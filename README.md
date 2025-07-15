## Lighterhouse Backend Project

**Google PageSpeed Insights API**를 활용한 웹 페이지 품질 분석 백엔드 서버입니다.

React 기반 프론트엔드와 연동되어 사용자가 입력한 URL의 Lighthouse 결과를 반환합니다.

### 기술 스택

- **Java 17+**
- **Spring Boot 3**
- **REST API** (`@RestController`)
- **Google PageSpeed Insights API**
- **AWS App Runner**
- **환경 변수 기반 시크릿 관리**

### 주요 기능

- URL 입력 시 Google PSI API 호출
- 성능, 접근성, SEO, Best Practices 항목 분석
- 프론트엔드에서 호출 가능한 RESTful API 제공  
  (`/api/analyze?url=https://example.com`)

### 디렉토리 구조

```bash
src/
├── main/
│ ├── java/com/example/lighterhouse_back/
│ │ ├── controller/PsiController.java
│ │ └── LighterhouseBackApplication.java
│ └── resources/
│ ├── application.properties (.env)
```

### 실행 방법 (로컬)

```bash
# 필수 환경 변수 설정
export GOOGLE_PSI_KEY=your_google_api_key

# 실행
./mvnw spring-boot:run
혹은 VS Code launch.json에서:

# .vscode/launch.json 에 env 주입
{
  "type": "java",
  "name": "Spring Boot",
  "request": "launch",
  "mainClass": "com.example.lighterhouse_back.LighterhouseBackApplication",
  "env": {
    "GOOGLE_PSI_KEY": "your_google_api_key"
  }
}
```

### 현재 배포된 방법 (AWS App Runner)

1. App Runner 설정

- Source: GitHub 연결

- Build command: ./mvnw package -DskipTests

- Start command: java -jar target/\*.jar

2. 환경 변수 설정:

- 실제 키는 application.properties에서 노출하지 않고 환경변수 ${GOOGLE_PSI_KEY}로 주입합니다.

### API 예시

```js
# GET /api/analyze?url=https://example.com
응답 (요약)

{
    "categories": {
        "performance": {...},
        "seo": {...},
        "accessibility": {...}
    },
    "audits": {...}
}
```

### 참고

- [Google PageSpeed Insights API Docs](https://developers.google.com/speed/docs/insights/v5/get-started?hl=ko)

- Spring Boot 3 / Java 17 이상 권장
