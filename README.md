# spring-gift-enhancement

## 1단계 구현사항
- 상품 카테고리 mvc 구현
- add.html 상품 추가시 카테고리 지정할 수 있도록
- 테스트코드 작성


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
            ├── member.html
            ├── data.sql
            ├── schema.sql
            ├── static
            └── templates
                ├── add.html
                ├── edit.html
                ├── list.html
                └── view.html             
└── src
    └── main
        ├── java
            └── gift   
                └── member.service   
                    └── MemberServiceTest.java