# spring-gift-enhancement

## 1단계 구현사항
- 상품 카테고리 mvc 구현

## 2단계 구현사항
- 상품에 옵션 정보 구현
- 옵션 네이밍 규칙
1) 공백 포함 최대 50자
2) 특수문자
    - 가능: ( ), [ ], +, -, &, /, _
    - 그 외 특수 문자 사용 불가
- 옵션 수량은 최소 1개 이상 1억 개 미만
- 옵션 이름 중복 허용 안함
- 컨트롤러 생성하여 HTTP 응답&요청 주고 받도록 구현
- 옵션 서비스(행위) 테스트 구현

## 3단계 구현사항
- 옵션 수량 차감 메소드들 추가
- @DataJpaTest 구현

## 현재 코드 구조
```plaintext
└── src
    └── main
        ├── java
        │   └── gift
        │       ├── config
        │       │   └── SecurityConfig.java
        │       ├── controller
        │       │   ├── AdminController.java
        │       │   ├── CategoryController.java
        │       │   ├── HomeController.java
        │       │   ├── MemberController.java
        │       │   ├── ProductConroller.java
        │       │   └── WishController.java
        │       ├── dto
        │       │   ├── MemberDto.java
        │       │   ├── OptionDto.java
        │       │   ├── ProductDto.java
        │       │   └── WishRequest.java
        │       ├── entity
        │       │   ├── Catogory.java
        │       │   ├── Member.java
        │       │   ├── Option.java
        │       │   ├── Product.java
        │       │   └── Wish.java
        │       ├── exception
        │       │   ├── CategoryNotFoundException.java
        │       │   ├── GlobalExceptionHandler.java
        │       │   ├── InvalidProductNameException.java
        │       │   └── ProductNotFoundException.java
        │       ├── repository
        │       │   ├── CategoryRepository.java
        │       │   ├── MemberRepository.java
        │       │   ├── OptionRepository.java
        │       │   ├── ProductRepository.java
        │       │   └── WishRepository.java
        │       ├── service
        │       │   ├── CategoryService.java
        │       │   ├── MemberService.java
        │       │   ├── ProductService.java
        │       │   └── WishService.java      
        │       └── Application.java
        └── resources
            ├── data.sql
            ├── schema.sql
            └── templates
                ├── add.html
                ├── edit.html
                ├── list.html
                └── view.html             
└── src
    └── test
        └── java
            └── gift   
                ├── CategoryRepositoryTest.java
                ├── MemberRepositoryTest.java
                ├── OptionRepositoryTest.java
                ├── ProductRepositoryTest.java
                └── WishRepositoryTest.java