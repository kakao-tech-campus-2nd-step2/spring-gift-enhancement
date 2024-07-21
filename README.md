# spring-gift-enhancement

## 기능 요구 사항
상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다.
- 별도의 HTTP API를 만들 필요는 없다.
- 서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.

## 프로그래밍 요구 사항
- 구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현한다.

## 힌트
```java
var option = optionRepository.findByProductId(productId).orElseThrow();
option.subtract(quantity);