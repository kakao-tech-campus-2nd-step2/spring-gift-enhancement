# spring-gift-enhancement
### 0단계
* dom 처리 메서드를 별도로 구분해서 호출하는 방식으로 변경
* PageRequest 적용
* HomeController에서 admin 분리하기
* null일때 NotFoundexception 추가
* findByEmail을 getMember로 변경 후 null exception 추가
* 유저용 전체 상품 목록 user-products 추가 (위시리스트로 상품을 담아주는 기능 구현)
### 1단계
* 카테고리 엔터티, dto, 컨트롤러, 서비스, 레퍼지토리 추가 구현
* product와 연관관계매핑
* 클라이언트단 구현 (카테고리 선택, 조회)
* dto를 사용해 필요한 데이터만 전송할 수 있도록 수정
