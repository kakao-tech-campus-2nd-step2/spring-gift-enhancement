# spring-gift-enhancement
- [1단계 - 상품 카테고리](#1단계---상품-카테고리)
  - [기능 요구 사항 정리](#기능-요구-사항-정리)
  - [프로그래밍 요구 사항 정리](#프로그래밍-요구-사항-정리)
  

---
# 1단계 - 상품 카테고리

## 기능 요구 사항 정리
상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.

- 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
- 카테고리는 수정할 수 있다.
- 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
- 카테고리의 예시는 아래와 같다.
  - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점

아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.

### Request
```
GET /api/categories HTTP/1.1
```

### Response
```
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
```

## 프로그래밍 요구 사항 정리
- 구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.

## 구현 기능 목록
- Category 모델 설계
  - id : Long
  - name : String
  - color : String
  - imageUrl : String
  - description : String
- Category 레포지토리 및 서비스 설계
- 상품과 카테고리 연관 관계 매핑
- Category API 추가
- 관리자 화면 카테고리 관련 기능 추가