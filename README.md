# 🛍️ MUSINSSAK (Spring Boot + React)
**설계가 명확하면, "코드를 치는 행위" 는 목표를 달성하는 "수단" 이 된다.**

**설계가 명확하지 않으면, "코드를 치는 행위" 는 불필요한 "노동" 이 된다.**

## 📖 웹 서비스 개요
**소개**  
무신삭(MUSINSSAK)은 무신사 홈페이지를 모방하여 구현한 **이커머스 쇼핑몰 플랫폼**입니다.  
사용자는 크롤링한 상품 데이터를 기반으로 **상품 선택 → 장바구니 담기 → 주문 → 결제**까지의 흐름을 경험할 수 있으며,  
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

---

## 📊 ER 다이어그램
데이터 모델 설명
[https://dbdiagram.io/d](https://dbdiagram.io/d/688c5d9ccca18e685cc6e2cf)
![ERD 다이어그램](/ERD.png)
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

## 🔨 서버 아키텍처
개발/운영 구조 (진행중)
![Architecture](/Architecture.png)

### Dev (Local · Docker Compose)
- 경로: **React Dev Server → Spring Boot App → Docker(Compose) → MySQL / Redis / Kafka / ZK / Scouter**
- 목적: 환경 재현성, 로컬 일괄 기동, Kafka-UI & Scouter로 가시성 확보

### Prod (AWS)
- 경로: **CloudFront+S3 → Nginx/ALB → EC2(App) → RDS / Redis / Kafka**
- **FE**: CloudFront+S3로 정적 파일(CSS/JS/이미지 등)을 CDN 캐싱해 전송 속도 최적화  
- **Nginx/ALB**: API 요청을 받아 EC2(App) 인스턴스로 **트래픽 분산** 처리  
- **BE**: EC2 컨테이너에서 Spring Boot 실행  
- **DB/캐시/메시징**: 초기엔 EC2+Compose로 시작 → 트래픽 증가 시 **RDS / ElastiCache / MSK**로 확장

### 배포 (CI/CD)
- GitHub Actions로 **빌드 → Docker 이미지 푸시 → EC2 자동 배포**  
- 인프라 생성/설정은 **콘솔 또는 IaC(Terraform 등)**로 관리

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

---

## 🔥 BE 챌린지 & 해결
문제 상황과 해결 과정 (성능, 동시성, 보안 등)

---

## 📂 프로젝트 구조
DDD/패키지 구조 및 설계 의도

---

## 🚀 CI/CD 파이프라인
Github Actions, Docker, 배포 자동화

---

## 📝 기타 산출물
WBS, 요구사항 명세, 회의록 등

---

## 📑 공유/학습 정리
협업 중 배운 점 / 기술 정리

---

## ✅ 핵심 요약
프로젝트 강점 3~4줄 요약

---
