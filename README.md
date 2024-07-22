# spring-gift-enhancement
## Step3
기능 목록
1. ProductOption 엔티티 및 레포지토리 추가
2. ProductOption 서비스 및 컨트롤러 추가
3. 상품 옵션 수량 차감 기능 구현
4. ProductOptionService 및 ProductOption에 대한 단위 테스트 추가
   
커밋 메시지
* feat: ProductOption 상품 옵션 수량 차감 기능 추가
* fix: subtract 메소드에서 수량 부족 처리
* test: 수량 차감 기능에 대한 단위 테스트 추가


## Step2
feat: 상품 옵션 엔티티 클래스 생성 및 데이터베이스 초기화 스크립트 작성
* Option 엔티티 클래스 생성:
    * 옵션 엔티티 클래스 생성 (옵션 이름, 수량, 상품과의 관계 포함)
    * 옵션 이름 최대 50자까지 입력 가능하도록 설정
    * 허용된 특수 문자: ( ), [ ], +, -, &, /, _
    * 옵션 수량 최소 1개 이상, 1억 개 미만으로 제한
* 데이터베이스 설계 및 초기화:
    * 상품 옵션 테이블 생성 SQL 스크립트 작성
    * 상품 테이블과 옵션 테이블 간의 관계 설정
    * 초기 데이터 삽입을 위한 SQL 스크립트 작성 (상품 옵션 데이터 삽입 포함)
    * 외래 키 제약 조건 위반 문제 해결을 위한 데이터 삽입 순서 조정
      
feat: 상품 옵션 인터페이스 클래스 로직 생성
* OptionRepository 인터페이스 생성:
    * 옵션 데이터 접근을 위한 레포지토리 인터페이스 작성
    * 상품과의 관계를 처리하기 위한 메서드 추가
      
feat: 상품 옵션 서비스 클래스 로직 생성
* OptionService 클래스 생성:
    * 옵션 추가, 수정, 삭제를 위한 비즈니스 로직 구현
    * 옵션 유효성 검사 로직 추가
    * 상품과의 관계 설정 로직 추가
      
feat: 상품 옵션 컨트롤러 클래스 로직 생성
* OptionController 클래스 생성:
    * HTTP 요청을 처리하기 위한 컨트롤러 생성
    * 옵션 추가, 수정, 삭제, 조회를 위한 API 엔드포인트 구현
    * 유효성 검사 및 에러 처리를 위한 로직 추가
      
feat: 상품 관련 리팩토링
* Product 엔티티 리팩토링:
    * 상품 엔티티에 옵션과의 관계 추가
* ProductService 리팩토링:
    * 상품과 옵션 관련 비즈니스 로직 개선
* ProductController 리팩토링:
    * 상품 관련 HTTP 요청 처리 로직 개선

feat: HTML 및 UI 개선
* product.html:
    * 상품 관련 HTML 파일 개선
* adminProductController:
    * 관리자 화면에서 상품, 카테고리 및 옵션을 관리할 수 있는 컨트롤러 추가
* productController:
    * 상품 관련 HTTP 요청 처리 로직 개선

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
