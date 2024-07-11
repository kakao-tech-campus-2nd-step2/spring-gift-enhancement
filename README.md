# spring-gift-jpa

## step1
> ### 요구사항
> 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다. 
>  - 엔티티 클래스와 리포지토리 클래스를 작성해 본다.
>  - @DataJpaTest를 사용하여 학습 테스트를 해 본다.

### 기능 목록
#### DB 작성 & 초기 데이터 입력
기존 SQL문으로 작성했던 `schema.sql`과 `data.sql`을 JPA를 사용하여 리팩터링한다.

#### Productㅎ
- `Product` 엔티티 클래스를 작성한다.
- HashMap 기반 `ProductRepository` 리포지토리 클래스를 JPA로 리팩터링한다.
- `@DataJpaTest`를 사용하여 학습 테스트를 진행한다.

#### User
- `User` 엔티티 클래스를 작성한다.
- JDBC 기반 `UserRepository` 리포지토리 클래스를 JPA로 리팩터링한다.
- `@DataJpaTest`를 사용하여 학습 테스트를 진행한다.

#### WishList
- `WishList` 엔티티 클래스를 작성한다.
- JDBC 기반 `WishListRepository` 리포지토리 클래스를 JPA로 리팩터링한다.
- `@DataJpaTest`를 사용하여 학습 테스트를 진행한다.

## step2
> ### 요구사항
> 지금까지 작성한 JdbcTemplate 기반 코드를 JPA로 리팩터링하고 실제 도메인 모델을 어떻게 구성하고 객체와 테이블을 어떻게 매핑해야 하는지 알아본다.
> - 객체의 참조와 테이블의 외래 키를 매핑해서 객체에서는 참조를 사용하고 테이블에서는 외래 키를 사용할 수 있도록 한다.

### 기능 목록
#### Product
- `Product` 엔티티에 판매자 필드를 추가한다.
- 상품 수정/삭제 요청 시 상품 판매자 or 관리자만 접근을 허용한다.
- "카카오" 키워드가 들어간 상품 등록 요청 시 관리자만 접근을 허용한다.
- product 조회시 찜(위시) 갯수도 함께 조회한다.

### User
- role 필드는 매직 스트링을 사용한다.

### WishList
- `WishList` 엔티티에 Product와 User에 대한 연관관계를 설정한다.
- WishList 조회 쿼리를 JPA 연관관계에 따라 수정한다. 