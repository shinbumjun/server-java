# 🛍️ 쇼핑몰 프로젝트 (Spring Boot + React)

## 🚀 프로젝트 개요
Spring Boot와 React를 활용한 쇼핑몰 프로젝트입니다.
주요 목표는 **상품 조회/검색(비회원 가능) → 회원 인증/인가 → 장바구니·주문 → 결제**까지 실제 쇼핑몰 서비스 흐름을 구현하는 것입니다.
백엔드와 프론트엔드 모두 직접 설계·구현하며 실무에 가까운 아키텍처를 학습했습니다.

---

## 🛠️ 기술 스택 (Tech Stack)

- **Language**: Java 17, TypeScript
- **Backend**: Spring Boot 3.x, Spring Data JPA, QueryDSL
- **Frontend**: React, Vite, React Query, Axios
- **Database**: MySQL 8.0
- **Infra**: Docker, Flyway (DB Migration), Redis (세션/토큰 관리)
- **Build**: Gradle
- **Auth**: JWT (Access + Refresh), Redis 저장소
- **Test**: Postman
- **협업 & CI/CD**: Git Flow, GitHub Issues/PR, CI/CD (진행 중)

---

## 📂 Git 전략 & 컨벤션

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
 │   └─ pages/        # 화면 단위

```

---

## 📑 공유/학습 정리
- **Git 사용법**: `git pull --ff-only`, `git stash` 로 안전 병합
- **DIP 원칙**: 고수준 모듈(서비스)은 저수준 구현체가 아니라 **인터페이스(추상화)**에 의존하고, 저수준 모듈은 이를 구현한다.
- **QueryDSL**: `ProductRepositoryImpl` 패턴, Proxy 자동 위임
- **Vite 환경 변수**: `VITE_` 접두사 필수, 타입 안정성 확보
- **Docker + Flyway**: DB 마이그레이션 버전 관리 및 추적 가능

---

## ✅ 핵심 요약
- **도메인 주도 설계 (DDD)**: 엔티티/서비스/파사드 역할 분리
- **안정적인 인증 구조**: JWT + Redis 기반 토큰 관리
- **실무형 아키텍처**: Docker, Flyway, QueryDSL, 전역 예외 처리
- **프론트/백엔드 연동**: React + Spring Boot + REST API
