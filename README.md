# spring-gift-enhancement

---
<details>
<summary><strong>0단계 - 기본 코드 준비</strong></summary>

- 3주차 코드 옮기기
</details>

---

<details>
<summary><strong>1단계 - 상품 카테고리</strong></summary>

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
</details>

---
<details>
<summary><strong>2단계 - 상품 옵션</strong></summary>

- 옵션 엔티티 생성
  - id
  - name
    - 공백 포함 최대 50자
    - (),[],+,-,&,/,_ 외 사용 불가
    - 동일한 상품 내의 옵션 이름은 중복 불가
  - quantity
    - 최소 1개 이상 1억 개 미만
  - Product
    - 옵션과 Product는 다대일, ManyToOne
- 옵션 DTO 생성
- DB 수정
  - schema.sql
    - 테이블 이름은 options
    - 다중 열 UNIQUE 사용
  - data.sql
- application.properties 수정
- 옵션 Repository 생성
- 옵션 Service 생성
  - 옵션 1개일 때 못지우게 설정
    - 상품 정보에 항상 하나 이상의 옵션이 있어야 한다.
- 옵션 Controller 생성
- productService 수정
  - Product 생성 시 임시 옵션 추가
    - 상품 정보에 항상 하나 이상의 옵션이 있어야 한다.
  - 상품 지우면 그에 해당하는 옵션들도 지워지도록
- Product 관련 Controller 수정
  - AdminController
  - ProductController
- HTML 추가 및 수정
  - option 관련 HTML 추가
    - option_list
    - add_option_form
    - edit_option_form
  - product_list 수정
    - 옵션 보기 버튼
  - 보기 좋게 대부분 버튼으로 만들기
- Test 코드 작성
  - 옵션 단위 테스트
  - 옵션 Repository 테스트
  - 옵션 Service 테스트
</details>
