# spring-gift-enhancement

---
## 0단계 - 기본 코드 준비
- 3주차 코드 옮기기
---
## 1단계 - 상품 카테고리
- 카테고리 엔티티 만들기
  - 필드는 id, name(unique)
- 카테고리 DTO 만들기
- db 수정
  - schema.sql 수정
  - data.sql 수정
- product에 카테코리 추가하기
  - 카테고리와 product 간의 연관 매핑
    - Product에 ManyToOne 사용
- ProductDTO 수정
- 카테고리 Repository 만들기
- ProductRepository 수정
- 카테고리 Service 만들기
- ProductService 수정
- 카테고리 Controller 만들기
- AdminController, ProductController 수정
- HTML 추가 및 수정
  - category HTML 추가
    - category_list
    - add_category_form
    - edit_category_form
  - product HTML 수정
    - product_list
    - add_product_form
    - edit_product_form
  - wishlist HTML 수정
- Test 코드 작성 및 수정
  - ProductTest
  - CategoryTest
  - ProductRepositoryTest
  - WishlistRepositoryTest
  - WishlistRepositoryN1Test
  - CategoryRepositoryTest
