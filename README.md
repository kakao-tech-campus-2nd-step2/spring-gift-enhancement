# spring-gift-enhancement

1. 생성한 코드
- CategoryController
- CategoryDTO
- CategoryService
- CategoryServiceImpl
- category.html

일단 HTTP에 GET으로 카테고리를 받아야 하기 때문에
product때랑 동일 하게 진행했습니다.
그 이후로 html로 카테고리 버튼을 구현하고 눌렀을때 
해달 카테고리 상품만 보이도록 수정했습니다.

2. 수정한 코드
- ProductController <br>
선택한 카테고리의 상품만 출력되도록 수정
- product <br>
카테고리의 번호와 , 이름 변수를 각각 생성

3. 신경쓴 부분
- addProduct / EditProduct <br>
처음에 카테고리변수를 string으로 해서 입력받으려고 했습니다.
하지만 상품수정, 추가시에 카테고리 지정을 해야하는데
이용자가 기존의 카테고리외의 카테고리를 입력하거나 ,
오타를 입력할 경우 예외 처리를 해야할것 같아서 번호로 만들었습니다.
이용자에게는 카테고리를 한글로 보여주고 , 내부에서는 value를 카테고리 번호로 설정
해서 서버로 넘겨주는 방식을 선택했씁니다.
