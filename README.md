# spring-gift-enhancement
## 🚀 1단계 - 상품 카테고리

### 진행 방식
미션은 과제 진행 요구 사항, 기능 요구 사항, 프로그래밍 요구 사항 세 가지로 구성되어 있다.
세 개의 요구 사항을 만족하기 위해 노력한다. 특히 기능을 구현하기 전에 기능 목록을 만들고, 기능 단위로 커밋 하는 방식으로 진행한다.
기능 요구 사항에 기재되지 않은 내용은 스스로 판단하여 구현한다.

### 기능 요구 사항
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

- 상품에는 항상 하나의 카테고리가 있어야 한다.
- 상품 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리의 예시는 아래와 같다.
  - 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...

### 구현할 기능
- [X] Catecory 엔티티 클래스 정의
- [X] Product와 Category 테이블 연관관계 매핑 (다대일)
- [X] Admin 상품 추가/수정 템플릿에 Category 필드 추가
- [X] ProductRequest, RESPONSE DTO에 카테고리 추가


## 🚀 2단계 - 상품 옵션
### 기능 요구 사항
- [ ] 상품 정보에 옵션을 추가한다. 상품과 옵션 모델 간의 관계를 고려하여 설계하고 구현한다.
  - 상품에는 항상 하나 이상의 옵션이 있어야 한다.
- [ ] 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
- [ ] 이름에 사용할 수 있는 특수 문자 ( ), [ ], +, -, &, /, _
- [ ] 옵션 수량은 최소 1개 이상 1억 개 미만이다.
- [ ] 중복된 옵션은 구매 시 고객에게 불편을 줄 수 있다. 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- [ ] (선택) 관리자 화면에서 옵션을 추가할 수 있다.