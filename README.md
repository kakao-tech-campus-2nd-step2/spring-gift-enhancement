# spring-gift-enhancement
## 1단계
* 카테고리 추가하기
  * id, name, imageUrl
  * 관리자 권한으로 카테고리 수정 기능
  * 상품 추가 시 카테고리 설정 가능
  * 카테고리(1) : 상품(N)
## 2단계
* 옵션 추가하기
  * id, name, quantity
  * 관리자 권한으로 옵션 추가 기능
  * 편의를 위해 임의로 옵션 수정 기능도 추가 (관리자 권한)
  * 상품 추가 시 옵션 설정 가능
  * 상품(1) : 옵션(N)
  * 일대다 관계이므로 일급컬렉션 사용