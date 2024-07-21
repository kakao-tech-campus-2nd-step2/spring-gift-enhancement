# spring-gift-enhancement

# 0단계

## 피드백 반영
- [x] JwtConfig : JwtTokenProvider 코드를 분리하기 보다는 메서드로 호출하도록 구현
- [x] WebConfig : 실제 사용하는 코드인지 한번 디버깅
  - [x] 사용자 정의 HandlerMethodArgumentResolver를 등록하기 위해서 WebMvcConfigurer를 구현함
- [x] MemberController : 설정값 한곳에 모으기
- [x] MemberService : 사용자 토큰 사용
- [x] ProductService : 메서드 사용해서 깔끔하게 정의하기
- [x] proporties : 파일 저장 UTF-8인지 확인
  - [x] Edit Custom VM Options : -Dfile.encoding=UTF-8 추가
  - [x] Tomcat Encoding 설정 : VM options  ->  -Dfile.encoding=UTF-8 설정
  - [x] intelliJ 파일 Encoding 설정 : settings/file encoding 의 Global Encoding / Project Encoding / Default encoding for properties files 모두 UTF-8설정
- [x] Test : 사용하는 값들 상단 변수로 등록해서 사용해보기

# 1단계

## 구현할 기능 목록
- [x] 카테고리 생성
  - [x] entity 생성
  - [x] 카테고리 목록은 enum으로 설정해도 되나요?
  - [x] repository 생성
  - [x] 상품과 관계 매핑 : 카테고리 1개에 여러 product 있을 수 있음
  
- [x] 상품 entity 수정
  - [x] 카테고리와 join

- [x] 카테고리 추가하면서 상품 service 수정
  - [x] 상품 추가 시 카테고리도 설정하도록 수정
    - [x] 해당 카테고리가 없으면, 새로 생성
    - [x] 해당 카테고리가 있으면, product 추가
  - [x] 상품 수정 시, 카테고리 업데이트하도록 수정

## 0단계 피드백
- [x] JwtConfig를 Component로 변경하고 autowired사용하여 호출하도록 수정
- [x] WebConfig의 addArgumentResolvers가 없었을 땐 어떻게 동작했을까? (comment 작성)
- [x] ProductController의 pagenation 관련 내용 requestParam 말고 pageable 객체 받아서 사용하도록 수정
  - [x] 요청에서 page=0&size=2&sort=amount,desc 하면 됨
- [x] webConfigTest 목적 설명

## 1단계 피드백
- [ ] product의 category를 List가 아니라 대분류>중분류>소분류 처럼 되도록 생각해보기
- [x] fk를 product쪽에만 두기
- [x] CreateProductDto에 category 포함
- [x] ProductService에서 CateggoryRepo 제거

# 2단계

## 구현할 기능 목록
- [x] Option Entity 추가
  - [x] Option Entity에는 id, option_name이 있음
  - [x] option_name은 공백 포함 최대 50자
  - [x] option_name은 ( ), [ ], +, -, &, /, _ 를 제외한 특수문자 사용 불가
  - [x] 같은 Product의 option_name은 유니크 해야함
    - [x] Option에 quantity 추가 (1개 이상 ~ 1억개 미만)

- [x] Option Repository 구현
  
- [x] Product Entity 수정
  - [x] Product에 하나 이상의 옵션을 추가하기 위해 List를 사용하여 옵션 저장
  
- [x] Product Controller 수정
  - [x] Option 추가되면서 관련 내용들 확인하고 수정할 것 있다면 수정
  - [x] 특정 Product의 Option 가져오기
  
## 2단계 피드백
- [x] Product에서 Update문 수정
  - product에서 Update문 삭제
  - UpdateProductDto에서 update하도록 수정
- [ ] UpdateProductDto jakarta validator 정상 작동할지?
- [x] UpdateProductDto DAO역할 침범하지는 않는지?
  - update를 product의 service code내에서 작성 완료
  - Option을 update시키는 코드는 Option controller에서 진행하는 것이 맞는지 고민이 된다.
- [x] ProductService의 Optionservice.getProductOptions 를 굳이 가져와야 하나?
  - controller에서 바로 OptionService.getProductOptions를 호출하도록 수정

# 3단계

## 구현할 기능 목록
- [x] 상품 옵션 수량을 지정된 숫자만큼 빼는 기능
  - [x] OptionService에서 해당 기능 구현
    - [x] 지정된 숫자가 1 이상인지 확인
    - [x] 남은 수량보다 지정된 숫자가 더 큰 경우 오류 출력
    - [x] (남은 수량 - 지정된 숫자)를 다시 남은 수량에 저장
    - [x] 수량을 빼서 저장하는 기능 Entity 클레스에서도 구현해보기
  - [ ] 테스트 코드 작성
    - [ ] 상품 추가
      - [ ] 카테고리가 잘 추가되는 지 확인
      - [ ] 옵션 추가도 잘 이루어지는 지 확인
    - [ ] 상품 수정
      - [ ] 카테고리가 잘 수정되는 지 확인
      - [ ] 옵션 수정도 잘 이루어지는 지 확인
    - [ ] 옵션 수량 차감

## 3단계 피드백
  - [ ] ProductService의 Option update 부분 N x N 호출이 아닌 다른 방식으로 호출하기
    - originOption은 HashMap으로 변환해서 ID를 키로 사용해보기 O(N)