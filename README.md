# spring-gift-enhancement

## step1
> ### 기능 요구 사항
> **상품 정보에 카테고리를 추가한다. 상품과 카테고리 모델 간의 관계를 고려하여 설계하고 구현한다.**
> - 상품에는 항상 하나의 카테고리가 있어야 한다.
>   - 상품 카테고리는 수정할 수 있다.
>   - 관리자 화면에서 상품을 추가할 때 카테고리를 지정할 수 있다.
> - 카테고리는 1차 카테고리만 있으며 2차 카테고리는 고려하지 않는다. 
> - 카테고리의 예시는 아래와 같다.
>   - 교환권, 상품권, 뷰티, 패션, 식품, 리빙/도서, 레저/스포츠, 아티스트/캐릭터, 유아동/반려, 디지털/가전, 카카오프렌즈, 트렌드 선물, 백화점, ...
>   
> **아래 예시와 같이 HTTP 메시지를 주고받도록 구현한다.**
> 
> Request
> ```
> GET /api/categories HTTP/1.1
> ```
> Response
> ```
> HTTP/1.1 200
> Content-Type: application/json
> 
> [
>    {
>       "id": 91,
>       "name": "교환권",
>       "color": "#6c95d1",
>       "imageUrl": "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png",
>       "description": ""
>   }
> ]
> ```

### 데이터베이스 테이블: Category
| 필드명         | 데이터 타입  | 설명           | 기타 조건              |
|-------------|---------|--------------|--------------------|
| id          | Long    | 위시리스트 고유 식별자 | Primary Key, 자동 생성 |
| name        | String  | 카테고리 이름      | unique key         |
| description | String  | 카테고리 설명      | null 허용            |
| is_active   | Boolean     | 활성화 상태      | 기본값: true    |

### 기능 목록
#### Product
- `Product` 엔티티에 `Category` 필드를 추가한다. 
- '/admin' 요청에서 상품을 추가할 때 카테고리를 지정할 수 있도록 한다.
  - 일반 요청일 땐 "기타" 상품 카테고리에 배정한다.
- 상품 카테고리 수정 기능을 구현한다.
- 카테고리별 상품 조회 기능을 구현한다.

#### Category
- `Category` 생성, 수정, 삭제 기능을 구현한다
  - 카테고리 이름은 중복될 수 없다.
  - 관리자 권한이 있는 요청 시에만 가능하다.
- 카테고리 조회 기능을 구현한다.