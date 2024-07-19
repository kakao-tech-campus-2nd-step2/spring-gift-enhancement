# spring-gift-enhancement
---
## 1단계 요구사항
### Category
- id
- name
- @OneToMany-Product
### Product
- @ManyToOne-Category
### CategoryRepo, Controller, Service
### ProductDTO
- name
- price
- url
- category_id
### CategoryDTO
- name
### ProductViewController
Product -> ProductDTO로 변경, CategoryDTO list추가해서 add_product.html 호출
showEditForm() -> ProductDTO로 변경
### ProductService
createProduct() -> 파라미터 ProductDTO로 변경
### add_product
category 반영
---
## 2단계 요구사항
### Option
- id
- name
- quantity
- @ManyToOne-Product
### OptionDTO
- name
- quantity
- productId
### CRUD
### OptionService
### Product_DTO
- OptionList
### Product
- OneToMany-List<Option>
### add_product
- option 입력 추가
---
## 3단계 요구사항
### Option
- subtract()
### OptionRepository
- findByProductId()
### OptionServiceTest
- 단위테스트들
