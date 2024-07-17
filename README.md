# spring-gift-enhancement

## 현재 코드 구조
```plaintext
└── src
    └── main
        ├── java
        │   └── gift
        │       ├── Application.java
        │       ├── admin
        │       │   └── AdminController.java
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