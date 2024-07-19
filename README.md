# spring-gift-enhancement

## 기능 목록 

### step2 : 상품 옵션 추가 
- [ ] 상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.
- - [x] 상품에는 항상 하나 이상의 옵션이 있어야 한다.
  - [x] 옵션 엔티티 
  - [x] 상품에 옵션추가

- [ ] 옵션이름 유효성

  - [ ] 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다. 
  - [ ] 특수 문자
      * 가능: ( ), [ ], +, -, &, /, _
      * 그 외 특수 문자 사용 불가 
  - [ ] 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- [ ] 옵션 수량 유효성 
    * 옵션 수량은 최소 1개 이상 1억 개 미만이다.


- [ ] (선택) 관리자 화면에서 옵션을 추가할 수 있다.

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다

Request
GET /api/products/1/options HTTP/1.1

Response
HTTP/1.1 200
Content-Type: application/json

[
{
"id": 464946561,
"name": "01. [Best] 시어버터 핸드 & 시어 스틱 립 밤",
"quantity": 969
}
]

- [] 테스트 추가 



###  step1 : 카테고리 추가 
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