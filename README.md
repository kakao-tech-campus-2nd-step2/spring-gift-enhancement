# spring-gift-enhancement

## 1단계
### 상품 카테고리 도입
- 각 상품은 하나의 카테고리를 가질 수 있다. (다대일 관계)
- 카테고리 자체를 수정할 수 있다.
- 상품의 카테고리 설정도 변경할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.

### Model
#### Product
- 멤버 변수로 Category 추가(not null)
- @ManyToOne
#### Category
- id(PK)
- name(Unique)
  - 20자 이하
  
### Repository
#### CategoryRepository
- save
- findAll
- findById
- update
- delete

### Service
#### CategoryService
- 카테고리 추가
- 카테고리 조회
- 카테고리 변경
- 카테고리 삭제
#### ProductService
- 상품에 카테고리 추가
- 상품에 카테고리 변경

### Controller
#### CategoryApiController
- 카테고리 조회/추가/변경 삭제 요청

#### ProductApiController
- 상품과 카테고리 복합 요청
  - 상품 추가 시 카테고리 설정
  - 상품의 카테고리 변경

## 2단계
### 상품 옵션 도입
- 각 상품은 하나 이상의 옵션을 가진다. (1:N 관계)
- 옵션 이름은 공백을 포함하여 50자까지 입력할 수 있다.
  - 특수 문자는 "( ), [ ], +, -, &, /, _"만 허용한다.
- 상품의 옵션 당 수량(재고)은 1~100,000,000개 이다.
- 동일한 상품 내 옵션의 이름은 중복될 수 없다.

### Model
#### Options
- id(pk)
- name(unique)
  - null 불가
  - 50자 이하
  - 일부 특수문자 허용
- quantity
  - 1개~1억개
- product
  - @ManyToOne

### Repository
#### OptionsRepository
- save
- findAll
- findById
- update
- delete

### Service
#### OptionsService
- 옵션 추가
- 옵션 조회
- 옵션 수정
  - 옵션명 변경
  - 재고 변경
- 옵션 삭제
  - 삭제하고 남은 옵션이 1개 이상일 경우에만 삭제가 가능함

### Controller
#### ProductApiController
- 상품과 옵션 복합 요청
  - 상품 생성
    - 최소 하나의 옵션과 함께 생성
  - 상품 조회
    - 모든 옵션 목록과 함께 제공
    - 특정 옵션과 함께 제공
  - 상품 삭제
    - 모든 옵션들도 함께 삭제
#### OptionsApiController
  - 옵션 생성
  - 옵션 변경
  - 옵션 삭제

## 3단계
### 상품 옵션 수량 차감 기능 구현
- 옵션에 대해 요구하는 수량만큼 기존 수량에서 차감한다.

### Service
- 옵션 수량 수정
  - 예외 상황 발생 시, OptionsQuantityException 쓰로잉
### Options
  - quantity validation
  - 차감하고 남은 수량 검증
  - 실제 Entity 내 quantity 값 수정