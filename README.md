# spring-gift-enhancement

## Step1

feat: 상품 및 카테고리 엔티티 클래스 생성 및 데이터베이스 초기화 스크립트 작성

- Category 엔티티 클래스 생성 (name, color, imageUrl, description 속성 포함)
- Product 엔티티 클래스 생성 (name, price, description, imageUrl, category 속성 포함)
- users, category, product, wishes 테이블 생성 SQL 스크립트 작성
- 초기 데이터 삽입을 위한 SQL 스크립트 작성 (카테고리 데이터 먼저 삽입 후 상품 데이터 삽입)
- 데이터 삽입 순서 조정으로 외래 키 제약 조건 위반 문제 해결

feat: 카테고리 인터페이스 클래스 로직 생성

- 카테고리 인터페이스 생성 (데이터 접근을 위한 인터페이스 작성)

feat: 카테고리 서비스 클래스 로직 생성

- 카테고리 서비스 클래스 생성 (비즈니스 로직 처리)

feat: 카테고리 컨트롤러 클래스 로직 생성

- 카테고리 컨트롤러 클래스 생성 (HTTP 요청 처리)
