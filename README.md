## 1단계 - 상품 카테고리
### 기능 요구 사항
#### 카테고리 추가
##### 카테고리 제한사항
- 상품에는 항상 하나의 카테고리가 존재
- 관리자 화면에서 상품 추가시 카테고리도 지정 필수
- 1차 카테고리만 고려

**Request**

`GET /api/categories HTTP/1.1`

**Response**
```json
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
### 프로그래밍 요구 사항
- 구현한 기능들에대한 테스트 코드 작성
