# spring-gift-enhancement

## 기능 목록 

### 카테고리 추가 
- [x] product 에 카테고리 추가 
- [x] category entity 추가 
- [x] product repository CRUD 수정 
    - [x] 상품추가시 카테고리 지정 가능하도록 
    - [x] 카테고리 목록 : 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...
- [] 테스트 기능 구현 

### 수정할부분 
- [x] price 타입을 Integer 로 변경

### http 예시 
Request
GET /api/categories HTTP/1.1
Response
HTTP/1.1 200
Content-Type: application/json

[
{
"id": 91,
"name": "교환권",
"color": "#6c95d1",
"imageUrl": "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png",
"description": ""
}
]

### DDL 참고 
create table category
(
id   bigint       not null auto_increment,
name varchar(255) not null,
primary key (id)
) engine=InnoDB

create table product
(
price       integer      not null,
category_id bigint       not null,
id          bigint       not null auto_increment,
name        varchar(15)  not null,
image_url   varchar(255) not null,
primary key (id)
) engine=InnoDB

alter table category
add constraint uk_category unique (name)

alter table product
add constraint fk_product_category_id_ref_category_id
foreign key (category_id)
references category (id)