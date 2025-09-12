# 🛍️ MUSINSSAK (Spring Boot + React)
**설계가 명확하면, "코드를 치는 행위" 는 목표를 달성하는 "수단" 이 된다.**

**설계가 명확하지 않으면, "코드를 치는 행위" 는 불필요한 "노동" 이 된다.**

## 📖 웹 서비스 개요
**소개**  
무신삭(MUSINSSAK)은 무신사 홈페이지를 모방하여 구현한 **이커머스 쇼핑몰 플랫폼**입니다.  

사용자는 크롤링한 상품 데이터를 기반으로 **상품 선택 → 장바구니 담기 → 주문 → 결제**까지의 흐름을 경험,  

추가로 **AI 챗봇 추천 기능**을 통해 개인화된 상품 추천과 구매 지원을 받을 수 있습니다.  

**개발 기간**  
2025.07.18(금) ~ 진행 중  

**개발 인원**  
AI 1명, 백엔드 2명, 프론트엔드 2명  

---

## 🔧 BE 기술 스택
| 영역 구분       | 기술 도구                       | 선정 이유 | 사용처 |
|----------------|--------------------------------|-----------|--------|
| 언어 & <br> 프레임워크 | Java 17, Spring Boot 3.5.3      | 최신 LTS 기반의 안정성과 풍부한 레퍼런스를 제공하는 Spring Boot 선택 | - REST API 서버 개발 <br> - 전자상거래 도메인 로직 구현 |
| ORM/JPA | Spring Data JPA, QueryDSL | JPA로 CRUD 단순화, QueryDSL로 <br> **무한 스크롤(커서 기반) 페이징** 및 동적 쿼리 구현 | - 상품 검색/조회 API <br> - 카테고리/브랜드 정렬·필터링 |
| DB            | MySQL 8.0                       | RDBMS 중 참고할 수 있는 레퍼런스가 가장 많은 MySQL을 선택했습니다. | - 상품 검색/조회 API <br> - 카테고리/브랜드 정렬·필터링 |
| DB 마이그레이션 | Flyway                          | DB 스키마 변경 이력을 버전 관리, 협업 충돌 최소화 | - `V1__init.sql` 기반 테이블 생성 <br> - 배포 시 마이그레이션 자동 적용 |
| 캐시/세션 <br> 관리 | Redis 7.0 | In-Memory 기반으로 세션/토큰 <br> 저장 및 분산 락 제공 | - Refresh Token 저장·검증 <br> - 로그아웃 시 토큰 삭제 <br> - 주문 동시성 제어 락 |
| 메시징 & <br> 이벤트 <br> (진행중) | Apache Kafka + Zookeeper        | 대규모 이벤트 처리 및 비동기 확장성 확보 | - 결제 완료 <br> → 주문 확정 이벤트 발행 <br> - 실시간 알림 이벤트 처리 |
| 보안 & <br> 인증    | Spring Security + JJWT          | JWT 기반 인증/인가로 무상태 아키텍처 구현 | - 로그인/로그아웃 API <br> - Access/Refresh  <br> → Token 발급 및 검증 |
| API 문서화     | Springdoc (Swagger UI)          | API 테스트 및 문서화를 빠르게 지원 | - `/swagger-ui.html`을 통한  <br> 개발/QA 협업 |
| 클라우드/ <br> 배포 <br> (진행중) | GitHub Actions, Docker Compose, AWS EC2 | GitHub Actions로 CI/CD 자동화, Docker Compose로 환경 일관성 확보, AWS EC2 프리티어로 비용 절감 | - main 브랜치 푸시 시  <br> 자동 빌드/배포 <br> - BE/DB/Redis/Kafka를 Docker Compose로 관리 <br> - AWS EC2에 배포 |
| 모니터링 <br> (진행중)    | Scouter APM                     | WAS 성능 및 트랜잭션 모니터링 지원 | - JVM/SQL 성능 모니터링 <br> - 실시간 로그 및 메트릭 확인 |
| 환경 변수 <br> 관리  | .env                   | 민감 정보 별도 관리로 보안 강화 | - DB 계정, JWT Secret, <br>Kakao API Key 관리 |
| 프론트엔드 | React(ts), Vite, Axios | 빠른 번들링(Vite)과 간단한 API 호출(Axios)로 효율적인 UI/데이터 연동 | - API 호출 |
| 빌드 도구 | Gradle | 의존성 관리 및 빌드 자동화 | - Spring Boot 프로젝트 빌드/테스트/배포 |
| 테스트/협업 | Postman, GitHub PR | API 테스트 및 협업 표준화 | - API 시나리오 테스트 <br> - 브랜치 전략 & 코드 리뷰 |


---

## 📊 ER 다이어그램
1) [ER 다이어그램 링크(클릭)](https://dbdiagram.io/d/688c5d9ccca18e685cc6e2cf)
<img width="720" height="360" alt="image" src="https://github.com/user-attachments/assets/6efd3314-31c6-41c3-a221-a0a2c6f5f408" />

**요약**
- 총 **테이블 34개**, **외래키 42개**
- 핵심 축: `users` → `orders` → `order_items` → `products` → `payments`

**도메인별 구성**
- **회원/계정 (4)**: 사용자/주소/파일/비밀번호 관리
- **브랜드·카테고리·상품 (6)**: 상품/옵션/이미지/배송정책
- **관심·최근·장바구니 (4)**: 찜/최근 본/장바구니
- **주문·재고 (4)**: 주문/주문상품/배송/재고예약
- **결제 (4)**: 결제/카드/카카오페이/아임포트
- **리뷰·문의 (7)**: 리뷰/문의/상품문의
- **쿠폰·적립금 (5)**: 쿠폰/사용내역/적립금/이력

---

## 🔨 서버 아키텍처 (개발/운영 구조 진행중)

<img width="720" height="360" alt="Architecture" src="https://github.com/user-attachments/assets/27af25ab-7cc3-4000-a13b-70e39cd8dcc1" />

### Dev (Local · Docker Compose)
- 경로: **React Dev Server → Spring Boot App → Docker(Compose) → MySQL / Redis / Kafka / ZK / Scouter**
- 목적: 환경 재현성, 로컬 일괄 기동, Kafka-UI & Scouter로 가시성 확보

### Prod (AWS)
- 경로: CloudFront+S3 → Nginx/ALB → EC2(App) → RDS / Redis / Kafka
- **FE**: CloudFront+S3로 정적 파일(CSS/JS/이미지 등)을 CDN 캐싱해 전송 속도 최적화  
- **Nginx/ALB**: API 요청을 받아 EC2(App) 인스턴스로 **트래픽 분산** 처리  
- **BE**: EC2 컨테이너에서 Spring Boot 실행  
- **DB/캐시/메시징**: 초기엔 EC2+Compose로 시작 → 트래픽 증가 시 **RDS / ElastiCache / MSK**로 확장

### 배포 (CI/CD)
- GitHub Actions로 빌드 → Docker 이미지 푸시 → EC2 자동 배포
- 인프라 생성/설정은 콘솔 또는 IaC(Terraform 등)로 관리

> **요약**: Dev는 간단히 한 번에 띄워 개발, Prod는 CDN·로드밸런서·매니지드 서비스로 안정성과 확장성 강화

---

## 📂 Git 전략 & 컨벤션
협업 방식, 커밋 컨벤션

### Git Flow
- `main`: 운영 브랜치
- `dev`: 개발 통합 브랜치
- `feature/*`: 기능 단위 브랜치 → dev 머지
- `dev → main`: 팀장 승인 후 머지

### Commit Message
- `feat`: 기능 추가
- `fix`: 버그 수정
- `docs`: 문서 수정
- `style`: 코드 스타일/UI
- `refactor`: 리팩토링
- `test`: 테스트 코드
- `chore`: 설정/빌드 작업

---

## 📌 주요 구현 내용
핵심 기능 상세 (JWT 인증, 상품 조회, 결제 등)

### 1. 인증/인가 (JWT + Redis)
- Access (~15분) / Refresh (~14일) 구조
- Refresh 토큰은 Redis에 저장 (`RT:{userId}`)
- `/api/auth/refresh`: Access 재발급
- `/api/auth/logout`: Redis Refresh 삭제 + 쿠키 만료
- **실패 응답 통일**
  ```json
  { "status": 401, "code": "AUTH_REQUIRED", "message": "로그인이 필요합니다." }
  ```

### 2. 전역 예외 처리
- **ErrorCode**: 상태·코드·메시지 정의
- **BusinessException**: 서비스에서 throw
- **GlobalExceptionHandler**: JSON 응답으로 변환

### 3. 상품 상세 조회 및 카테고리 페이지
- 상품 + 브랜드 + 이미지 + 옵션 + 리뷰 집계
- **Facade 패턴 적용**
  - Service: 조회 규칙, 예외 처리
  - Facade: DTO 조립
- QueryDSL로 정렬/페이징 최적화

### 4. 카카오 주소검색 API
- REST API 키 → `.env` 관리
- RestTemplate Client → Service → Controller 분리
- 응답 DTO: 도로명, 지번, 우편번호, 건물명 등 변환

### 5. 결제
- 카드사 인증 → 서버 응답 → PortOne(아임포트) 연동
- 기능: 결제 / 취소 / 환불
- 보안 및 상태 동기화 고려

### 6. 프론트엔드 구조 (Atomic Design)

```bash
src/
 ├─ api/              # API 호출 함수
 ├─ components/
 │   ├─ atoms/        # input, button 등 최소 단위
 │   ├─ molecules/    # 주소 리스트 아이템 등
 │   ├─ organisms/    # atoms + molecules 조합 (모달 등)
 │   ├─ templates/    # 레이아웃/섹션 단위
 ├─ pages/            # 화면 단위

```

---

## 🔥 BE 챌린지 & 해결 (문제 상황과 해결 과정 (성능, 동시성, 보안 등) 진행 중)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/200c36f0-c372-4c70-9e2d-b4b21c495689" />


- 현재 Scouter APM 기반 모니터링 성능 분석
- 서버 부하 테스트 및 병목 지점 확인 → 성능 개선 아이디어 정리 중
- 진행 상황은 이미지(Scouter 대시보드 캡처)와 함께 업데이트 예정

---

## 📂 프로젝트 구조
```bash
musinssak/
├─ api/                          # 외부 노출 레이어 (입·출력 경계)
│  ├─ <domain>/                  # 예: product, order, auth ...
│  │  ├─ dto/                    # 요청/응답 DTO
│  │  └─ facade/                 # 유즈케이스 조합/흐름 조정
│  └─ <domain>Controller.java    # 각 도메인 컨트롤러
│
├─ common/                       # 전역 공통 모듈
│  ├─ exception/                 # ErrorCode, 예외 계층, 글로벌 핸들러
│  └─ web/                       # 공통 응답/필터/리졸버 등 웹 유틸
│
├─ domain/                       # 핵심 도메인 (비즈니스 규칙)
│  ├─ <domain>/
│  │  ├─ entity/                 # JPA 엔티티(애그리게잇 루트 중심)
│  │  ├─ repository/             # JPA/QueryDSL 리포지토리 (도메인 관점)
│  │  └─ service/                # 도메인 서비스(트랜잭션, 정합성)
│  └─ ...
│
├─ infra/                        # 외부 시스템 어댑터 (구현 상세)
│  ├─ external/                  # 카카오 주소검색 등 외부 API 클라이언트
│  ├─ openapi/                   # Swagger/Springdoc(OpenAPI) 설정/그룹화
│  ├─ security/                  # JWT, Security 설정/필터/인증 인프라
│  └─ test/                      # 통합 테스트 보조(도커/슬라이스 등)

```
- 레이어 분리:
api(입·출력) ↔ domain(규칙) ↔ infra(외부/구현)로 나눠 관심사 분리 & 교체 용이성 확보.
- 도메인 중심:
비즈니스 규칙은 domain에 집중(엔티티/서비스/리포지토리), 컨트롤러는 얇게 유지.
- 파사드 도입:
api/<domain>/facade가 유즈케이스 오케스트레이션과 DTO 조립 담당 → 서비스 간 순환참조 방지.
- 외부 연동 캡슐화:
infra/external에 외부 API 클라이언트를 모아 에러/재시도/DTO 매핑을 표준화.
- 문서·보안 표준화:
infra/openapi(API 문서), infra/security(JWT/필터)로 일관된 진입/문서/인증 체계 제공.
- 테스트 재현성:
infra/test로 통합 테스트 환경 보조(도커/슬라이스) → 안정적 회귀 검증.

---

## 📝 기타 산출물 
1) [노션 링크(클릭)](https://www.notion.so/MUSINSSAK-232927b3f19680409c40fa27f7e3a326)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/a7156a23-d22f-4720-8755-49c95a49877a" />

2) [figma-Readdy(클릭)](https://www.figma.com/design/fc8K0PjzNDRMaa2yMVdnxj/MUSINSSAK?node-id=0-1&p=f&t=wXhDCAwIFIpJStCd-0)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/a0ffd10c-1ddb-42e7-9d2e-9dbb26c9caa8" />

3) [miro 요구사항 분석(클릭)](https://miro.com/app/board/uXjVJcIUoKA=/)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/0aee721b-d9be-4d42-b19a-03a36de019ce" />

4) [API 명세서(클릭)](https://purple-meal-14c.notion.site/API-23b927b3f19680068d5dc4fc2275ec41?source=copy_link)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/d5f95965-104b-4753-98ad-12b675e9812e" />

5) [dbdiagram(클릭)](https://dbdiagram.io/d/688c5d9ccca18e685cc6e2cf)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/6efd3314-31c6-41c3-a221-a0a2c6f5f408" />

6) swagger
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/fd5f5705-94c6-431a-99fb-3c06b73768d8" />

8) [작업리스트 엑셀(클릭)](https://docs.google.com/spreadsheets/d/1Vh68-0KNRvekb--CbsOYb6aNiFGwJ9OOL2VSzWvxTZc/edit?gid=0#gid=0)
<img width="600" height="300" alt="image" src="https://github.com/user-attachments/assets/fc44006b-4040-48a8-a0ff-c89e67c10375" />

9) Sequence Diagram
<img width="600" height="300" alt="image (11)" src="https://github.com/user-attachments/assets/209e17bc-57bd-407f-ab67-566c14019bc9" />

---

## 📑 공유/학습 정리
[공유/학습 정리 링크(클릭)](https://www.notion.so/232927b3f19680de8ec8e080f5a4085b?v=232927b3f196802d8677000c22dcb9b3&source=copy_link)
- **개발 환경 & 배포**
  - Docker + MySQL + Spring Boot + React 연동
  - Flyway로 DB 마이그레이션 관리
  - Git 브랜치 전략 & 커밋 컨벤션
  - GitHub Actions 기반 CI/CD (진행 중)

- **백엔드 핵심 기술**
  - Spring Data JPA + QueryDSL: **한 빈(Repository)·두 타깃 구조**
  - 전역 예외 처리: `ErrorCode → BusinessException → GlobalHandler`
  - JWT 기반 인증/인가 + Redis Refresh Token
  - 카카오 주소검색 API 연동

- **프론트/환경 설정**
  - Vite 환경 변수(`VITE_` 접두사) 타입 안정성 확보
  - React 프로젝트 실행/연동 확인

- **기능 구현 사례**
  - 상품 상세 조회 (브랜드·옵션·리뷰 집계 + Facade 패턴)
  - Swagger(OpenAPI) 문서화 (Code-First 방식)
  - 모니터링: Scouter APM (진행 중)

- **기타**
  - DIP 원칙 정리 (엔티티·레포지토리·서비스 참조 구조)
  - Postman으로 JWT API 테스트
  - ChatGPT 프롬프트 학습 활용


---

## ✅ 핵심 요약
- **도메인 주도 설계 (DDD)**: 엔티티/서비스/파사드 역할 분리
- **안정적인 인증 구조**: JWT + Redis 기반 토큰 관리
- **실무형 아키텍처**: Docker, Flyway, QueryDSL, 전역 예외 처리
- **프론트/백엔드 연동**: React + Spring Boot + REST API

---
