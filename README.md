# spring-gift-enhancement

## 기능 구현

STEP1. 카테고리 
- 상품 추가 시 카테고리 지정
- 상품 수정 시 카테고리도 수정 가능
- 예시) 교환권, 상품권, 뷰티, 패션, 리빙/도서, 레저/스포츠 등

예시와 같이 HTTP 메시지를 주고받도록 구현한다.

Request
```
GET /api/categories HTTP/1.1
```
Response
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