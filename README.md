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
#### ProductApiController
- 상품과 카테고리 복합 요청
  - 상품 추가 시 카테고리 설정
  - 상품의 카테고리 변경
