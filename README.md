# spring-gift-enhancement

## 작업 배경

- 카테고리를 추가하여 상품 정보를 확장하고자 함

## 구현 범위

1. 상품에는 항상 하나의 카테고리가 있어야 한다. (항상 단 하나)
    - 상품 카테고리는 수정할 수 있다.
    - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
2. 카테고리는 1차 카테고리만 있으며, 2차 카테고리는 고려하지 않는다.
3. 카테고리의 예시는 아래와 같다.
    - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...

## 작업 방식

1. 카테고리 도메인 엔티티 클래스 추가
2. 상품 엔티티와 연관 관계 매핑 (다대일 단방향, 상품 쪽이 '다', 상품이 연관 관계의 주인)
3. 카테고리 전체 조회, ID 조회, 추가, 수정, 삭제 기능 구현
4. 상품 도메인 관련 계층 구조 수정
