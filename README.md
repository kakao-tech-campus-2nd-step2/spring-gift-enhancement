# spring-gift-enhancement

### 1단계 요구사항
#### 상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

- [x] 상품에는 항상 하나의 카테고리가 있어야 한다.
- [ ] 상품 카테고리는 수정할 수 있다.
- [ ] 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- [x] 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- [x] 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...

TodoList
- [x] categoryController
  - [x] 전부 보여주기
  - [x] 1개만 보여주기
  - [x] 추가하기
  - [x] 수정하기
  - [x] 삭제하기
- [x] categoryRequestDto
- [x] categoryResponseDto
- [x] categoryEntity
- [x] categoryService
  - [x] 전부 보여주기
  - [x] 1개만 보여주기
  - [x] 추가하기
  - [x] 수정하기
  - [x] 삭제하기
- [x] categoryRepository
- [x] product 에 category 추가
- [ ] product category 변경 기능 추가
  - [x] productService
  - [x] productRequestDto
  - [ ] adminController
