# spring-gift-enhancement

## 1단계 구현사항
- 상품 카테고리 mvc 구현
- add.html 상품 추가시 카테고리 지정할 수 있도록
- 테스트코드 작성

## 2단계 구현사항
- 상품에 옵션 정보 구현, 상품과 옵션 모델 간 관계 고려(?)
- 옵션 네이밍 규칙
1) 공백 포함 최대 50자
2) 특수문자
    - 가능: ( ), [ ], +, -, &, /, _
    - 그 외 특수 문자 사용 불가
- 옵션 수량은 최소 1개 이상 1억 개 미만
- 옵션 이름 중복 허용 안함
- 컨트롤러 생성하여 HTTP 응답&요청 주고 받도록 구현
- 옵션 서비스(행위) 테스트 구현


## 현재 코드 구조
```plaintext
└── src
    └── main
        ├── java
        │   └── gift
        │       ├── Application.java
        │       ├── admin
        │       │   └── AdminController.java
        │       ├── category
        │       │   ├── controller
        │       │   │    └── categoryController.java
        │       │   ├── model
        │       │   │    └── category.java
        │       │   ├── repostitory
        │       │   │    └── categoryRepository.java
        │       │   └── service
        │       │        └── categoryService.java        
        │       ├── exception
        │       │   ├── ForbiddenException.java
        │       │   ├── UnauthorizedException.java
        │       │   ├── KakaoProductException.java
        │       │   └── GlobalExceptionHandler.java
        │       ├── member
        │       │   ├── controller
        │       │   │    └── MemberController.java
        │       │   ├── dto
        │       │   │    └── MemberDto.java
        │       │   ├── model
        │       │   │    └── Member.java
        │       │   ├── repostitory
        │       │   │    └── MemberRepository.java
        │       │   └── service
        │       │        ├── MemberService.java
        │       │        └── TokenService.java
        │       ├── option
        │       │   ├── controller
        │       │   │    └── OptionController.java
        │       │   ├── model
        │       │   │    └── Opion.java
        │       │   ├── repository
        │       │   │    └── OptionRepository.java
        │       │   └── service
        │       │        └── OpionService.java
        │       ├── product
        │       │   ├── controller
        │       │   │    └── ProductController
        │       │   ├── dto
        │       │   │    └── ProductDto.java
        │       │   ├── model
        │       │   │    ├── Product.java
        │       │   │    └── ProductName.java
        │       │   ├── repository
        │       │   │    └── ProductRepository.java
        │       │   └── service
        │       │        └── ProductService.java
        │       └── wishlist
        │           ├── WishList.java
        │           ├── WishListController.java
        │           ├── WishListRepository.java
        │           └── WishListService.java      
        └── resources
            ├── sql
            │   └── schema.sql
            ├── static
            └── templates
                ├── add.html
                ├── edit.html
                ├── list.html
                └── view.html             
└── src
    └── main
        └── java
            └── gift   
                ├── OptionServiceTest.java
                ├── CategoryServiceTest.java
                └── MemberServiceTest.java
