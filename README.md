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
* 아마 mock 사용해야 할 것 같음...
* 이런 느낌으로...
* ```
  package gift.service;


  import gift.entity.Category;
  import gift.entity.CategoryDTO;
  import gift.repository.CategoryRepository;
  import org.assertj.core.api.Assertions;
  import org.junit.jupiter.api.Test;
  
  import java.util.Optional;
  
  import static org.mockito.ArgumentMatchers.any;
  import static org.mockito.Mockito.mock;
  import static org.mockito.Mockito.when;
  
  public class CategoryServiceTest {
  
      private CategoryService categoryService = mock(CategoryService.class);
      private CategoryRepository categoryRepository = mock(CategoryRepository.class);
  
      @Test
      void getCategories() {
          when(categoryRepository.findByName(any()))
                  .thenReturn(Optional.of(new Category(1L, "abc", "#FFFFFF", "test.com", "desc")));
  
          Assertions.assertThatNoException()
                  .isThrownBy(() -> categoryService.update(new CategoryDTO("def", "#000000", "test1.com", "desc1")));
      }
  }
  ```
---
- [x] category의 CRUD 구현
- [ ] category - product의 oneToMany 관계 매핑 
- [ ] productDTO에서 category 이름을 받으면 해당 이름으로 category 찾아서 저장하는 방식으로
    * CategoryRepository에서 findByName 필요할 듯

product는 무조건 하나의 category를 가지고 있어야 하는데 생각해 보니 product에 mapping 된 
category가 있을 떄 해당 category가 삭제되면 product의 category는 null이 되는데 이걸 
어떻게 처리해야할까 이걸?
-> 카테고리 삭제 제한: 만약 Product가 특정 Category를 반드시 가져야 한다면, Category가 
Product에 매핑되어 있을 때는 Category를 삭제하지 못하도록 제한, 
카테고리 삭제 전에 관련된 모든 Product를 확인하는 로직을 추가해야...