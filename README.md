# spring-gift-enhancement
# step0

- [x] 피드백 반영 리팩토링
  - [x] 스프링 컨테이너가 띄워지지 않는 환경으로 단위테스트 작성
  - [x] 비즈니즈 정책에 대한 유효성 검사를 Domain에서 수행하도록 변경
  - [x] Controller의 메서드 반환 타입을 구체적으로 작성
  - [x] testImplementation("org.assertj:assertj-core:3.25.1") 의존성 제거
  - [x] 지연 로딩 적용
# step1
- [x] 상품 카테고리 구현
  - [x] 상품 카테고리 추가
  - [x] 상품 카테고리 수정
  - [x] 상품 카테고리 삭제
  - [x] 상품 카테고리 조회
- [x] Product에 카테고리 속성 추가
- [x] Test 작성
  - [x] categoryController
  - [x] categoryService
  - [x] categoryRepository
# step2
- [x] 상품 옵션 구현
  - [x] 상품 옵션 CRUD
  - [x] 상품에는 항상 하나 이상의 옵션이 있어야 한다.
  - [x] 옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.
  - [x] 옵션 이름에 ( ), [ ], +, -, &, /, _ 외 특수 문자 사용 불가
  - [x] 옵션 수량은 최소 1개 이상 1억 개 미만이다.
  - [x] 동일한 상품 내의 옵션 이름은 중복될 수 없다.
- [x] 테스트 구현
  - [x] ProductControllerTest
  - [x] ProductRepositoryTest
  - [x] ProductServiceTest
  - [x] OptionRepositoryTest
  - [x] OptionServiceTest
- [ ] 피드백 반영
  - [x] gson 버전 정보 삭제
  - [x] API 요청 url을 복수형으로 변경
  - [x] 제네릭을 구체적으로 변경
  - [x] 불필요한 setter 최소화
  - [ ] mockito의 verify 기능을 활용하여 deleteCategoryTest() 구현
  - [ ] addCategoryController 수정
