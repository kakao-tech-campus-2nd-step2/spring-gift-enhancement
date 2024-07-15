# spring-gift-enhancement

## 기능 요구 사항
1. 상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.
    - 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다.
    - 카테고리는 수정할 수 있다.
    - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
    - 카테고리의 예시는 아래와 같다.
      - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점
2. 아래 예시와 같이 HTTP 메시지를 주고 받도록 구현한다.
   
    **Request**
    ```
   GET /api/categories HTTP/1.1
   ```    
   **Response**
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
3. 
