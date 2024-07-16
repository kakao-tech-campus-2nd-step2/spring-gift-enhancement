# spring-gift-enhancement
## step 1
### 기능 요구 사항
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점
### 구현할 기능 목록
- [x] Category domain model 과 DTO model 생성 / Product Entity 와 연관관계 매핑
- [x] Category Entity 에 대한 Repository, Service, Controller 구현
- [x] 관리자 화면에 카테고리를 지정 / 수정할 수 있도록 구현
- [x] 테스트 코드 작성
## step 2
### 기능 요구 사항
상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나 이상의 옵션이 있어야 한다.
- 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
- 특수 문자 가능: ( ), [ ], +, -, &, /, _ 
  - 그 외 특수 문자 사용 불가
- 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- (선택) 관리자 화면에서 옵션을 추가할 수 있다.
### 구현할 기능 목록
- [x] Option Entity 와 DTO 모델 생성 / Product Entity 와 연관관계 매핑
- [x] Option Entity 에 대한 Repository, Service, Controller 구현
- [ ] 관리자 화면에 옵션을 지정 / 수정할 수 있도록 구현 (선택사항)
- [ ] 테스트 코드 작성