# spring-gift-enhancement

### Step1

1. setter: 과제진행을 위해 builder pattern 다시 set__ 
2. 카테고리 기능 추가
    - Category 모델 생성
    - Category - Product 간 관계 설정
    - Category CRUD : CategoryRepository, CategoryService, CategoryController
    - 상품 add, delete 시 카테고리 기능도 함께
    - html 카테고리 기능 추가 : product/new,product/index,product/edit,user-products,user-wishes
3. 카테고리 테스트 코드 : CategoryServiceTest
4. setter 수정 및 Category 기능 추가에 따른 기존 테스트 코드들 수정

### Step2

1. ProductOption 기능 추가
2. product_option 테이블 추가
3. product/options.html 추가 및 기존 view 수정
4. option 기능에 대한 ProductOptionServiceTest 추가

### Step3

1. 상품수량 차감 기능 추가
2. 기존 수량보다 큰 수 입력 시 에러
3. 상품수량 차감 테스트코드 추가
4. product/options.html 에서도 기능 구현