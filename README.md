# spring-gift-enhancement

## step 1

### 기능 요구 사항

* 상품 정보에 카테고리를 추가하기
* 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현하기

---

* **카테고리는 1차 카테고리만 있으며** 2차 카테고리는 고려하지 않는다.
* 카테고리는 수정할 수 있다.
* 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
* 카테고리의 예시는 아래와 같다.
    * 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점

#### Request

```
GET /api/categories HTTP/1.1
```

#### Response

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

### 프로그래밍 요구 사항

구현한 기능에 대해 **적절한 테스트 전략**을 생각하고 작성한다.
---

- [x] category의 CRUD 구현
- [x] category - product의 oneToMany 관계 매핑
- [x] category가 삭제될 때 -> product의 category가 [DefaultCategory]로 변함
- [x] category가 삭제될 때 product의 category 테스트 &uarr;
- [x] product의 category가 변경될 때 -> 이전 category에 접근해서 product 삭제 & 새 category에 해당 product add
- [x] product의 category가 변경될 때 테스트 &uarr;
