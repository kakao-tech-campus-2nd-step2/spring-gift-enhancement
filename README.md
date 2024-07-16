상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

1. 상품에는 항상 하나의 카테고리가 있어야 한다.
2. 상품 카테고리는 수정할 수 있다.
3. 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
4. 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.

id와 카테고리 이름을 필드로 가지는 엔티티 객체 category 생성 - category, categoryDTO
category를 관리하고 저장하는 기능 - categoryController, categoryService, categoryRepository
category 관리 상호작용 웹페이지 - categories.html, Add_category.html, Edit_category.html

category에 제품이 있다면, product와 함께 카테고리 추가(category안에 있는 항목들에 한해서 상품 관리에서 category수정 가능)
