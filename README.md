# spring-gift-enhancement

## 기능 목록 

### step3 : 상품옵션 수량 차감 

- 기능 요구 사항
상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다.
별도의 HTTP API를 만들 필요는 없다.
서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.

- 프로그래밍 요구 사항
구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.
단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현한다.

- 힌트
var option = optionRepository.findByProductId(productId).orElseThrow();
option.subtract(quantity)

- [x] 옵션의 수량 값을 받은 매개변수 값만큼 줄이는 메서드 구현 
- [ ] Repository 에서 값을 가져올때 예외처리 구현 => 테스트에서 할일임 
- [ ] 단위테스트 구현 

### 코드리뷰 개선사항 :step1 
- [x] Controller 단에서 상태코드를 포함해서 반환 
- [x] UserResponseDto 가 도메인 계층 Member 를 의존 중 -> 변환은 서비스 로직에서 처리 !!! 
- [x] Dto 에서 도메인 계층을 의존중인 부분 수정 (Service 에서 변환하도록)
- [x] WishRepositoryTest 구현 
- [x] RepositoryInterface 명칭에서 Interface 삭제
- [x] Entity 에서 리스트를 사용하는 곳이 없는경우, 단방향 맵핑 사용하기
- [x] 실행시 오류발생 해결 

### 추가 개선사항 : step1
- [x] product 의 name 길이를 15로 줄이기 (요구사항 name varchar(15) not null 반영)
- [x] category의 color 길이를 7로 줄이기 (요구사항 color varchar(7) not null 반영)
- [ ] JWT 공부해서 적용 
- [x] 토큰 저장 기능 삭제 
- [x] product Dto 에서 유효성 검증 코드는 Service로 넘기고 Service에서 Dto 를 Entity로 변환할때 유효성 검증 진행하도록 
- [ ] TokenInterceptor 사용 
- [ ] 테스트 코드 작성

### step2 : 상품 옵션 추가 
- [ ] 상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.
- - [x] 상품에는 항상 하나 이상의 옵션이 있어야 한다.
  - [x] 옵션 엔티티 
  - [x] 상품에 옵션추가

- [x] 옵션이름 유효성
  - [x] 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다. 
  - [x] 특수 문자
      * 가능: ( ), [ ], +, -, &, /, _
      * 그 외 특수 문자 사용 불가 
  - [x] 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.

- [x] 옵션 수량 유효성 
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