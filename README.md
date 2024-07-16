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
