![image](https://github.com/user-attachments/assets/c4c61a25-fecc-4aa4-a076-58ff547e3f18)

# 쿠러미

흩어져 있는 인천대 제휴 혜택을 한 곳으로 모아 쿠폰 형식으로 사용할 수 있는 O2O 서비스입니다.

# ERD

![image](https://github.com/user-attachments/assets/b5088cc0-a608-4cbb-91ba-ee12fba0e89f)


# 🗺️아키텍처

![image](https://github.com/user-attachments/assets/ed64ed80-2798-40b1-9a33-5ff46c3455cd)


# 💡사용 기술

- Java 17
- SpringBoot 3.3.3
- JPA
- Querydsl
- Mysql
- Oracle
- Ngrinder
- Prometheus
- Grafana
- Sentry
- Gradle

# 🔍 주요 기능

### 회원

- 로그인 (인천대학교 포탈 아이디 연동)
- 회원 탈퇴( 회원 정보 삭제) - 예정
- 알림 기능 허용/거부(FCM 토큰 저장/삭제)
- 회원이 발급한 쿠폰/ 찜한 매장/ 조회
- 발급 쿠폰 사용

### 사장님 - 어플리케이션 분리 예정

- 매장 생성/수정/삭제
- 매장 별 쿠폰 발급/수정/삭제/조회

### 매장

- 전체/카테고리 별 매장 조회
- 매장 상세 조회(매장 이미지,쿠폰 리스트)
- 매장 찜하기

### 쿠폰

- 인기/최신 순/마감 임박 쿠폰 조회

### 임시 매장(사용자가 제휴했으면 하는 매장)

- 공감하기(공감 수가 일정 이상이면 해당 매장과 컨택)
- 전체/카테고리 별 임시 매장 조회
- 달성 임박 임시 매장 조회

### 검색

- 매장/임시 매장 검색

### 지도

- 지역(시/도/구) 별 매장 검색

### 알림

- 사장님 쿠폰 발급 시 해당 매장 찜한 사용자에게 알림

# 📌 개발 과정, 기술적 고민(트러블 슈팅)

- **다중 DB(학생 인증 DB - Oracle, 서버 DB - Mysql) 연결을 위한 Config 설정**
- 검색 성능 향상을 위한 Mysql full-text-index 도입
- FCM 알림 구현 및 성능 개선을 위한 비동기 전환- https://kangwook.tistory.com/44
- FCM 비동기 처리로 인한 문제점 해결(트랜잭션 롤백,예외처리) - https://kangwook.tistory.com/45
- 동시성 제어를 했음에도 사용자가 동일한 쿠폰 발급 가능한 문제 해결 - https://kangwook.tistory.com/50
- JWT 인증 정보를 캐싱함으로써 API 응답속도 20% 개선 - https://kangwook.tistory.com/56
- 쿼리 튜닝을 통한 매장 조회 성능 개선 - https://kangwook.tistory.com/53
