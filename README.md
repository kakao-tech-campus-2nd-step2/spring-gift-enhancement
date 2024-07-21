# spring-gift-enhancement

## 기능 요구 사항

- 상품 옵션의 수량을 지정된 숫자만큼 빼는 기능을 구현한다.
    - 별도의 HTTP API를 만들 필요는 없다.
- 서비스 클래스 또는 엔티티 클래스에서 기능을 구현하고 나중에 사용할 수 있도록 한다.

## 프로그래밍 요구 사항

- 구현한 기능에 대해 적절한 테스트 전략을 생각하고 작성한다.
- 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드에 대해 단위 테스트를 구현한다.

## 힌트

```
var option = optionRepository.findByProductId(productId).orElseThrow();
option.subtract(quantity);
```

## 구현 기능 목록

1. 옵션의 수량을 차감하는 메서드의 테스트 코드를 구현한다.
2. 옵션의 수량을 차감하는 메서드를 `OptionService`에서 구현한다.
    1. 입력으로 해당 product의 id값, option의 id값, 차감을 원하는 숫자를 입력 받는다.
        - 만일, 차감을 원하는 숫자를 음수로 입력할 시 `IllegalArgumentException`을
          발생시키고, `OPTION_SUBTRACT_NOT_ALLOW_NEGATIVE_NUMBER`을 반환한다.
    2. product의 id값을 확인해 존재하는 product인지 확인한다.
        - 만일, 존재하지 않는 product면 `IllegalArgumentException`을 발생시키고, `PRODUCT_NOT_FOUND` 메시지를 반환한다.
    3. option의 id값을 확인해 존재하는 option인지 확인한다.
        - 만일, 존재하지 않는 option이면 `IllegalArgumentException`을 발생시키고, `OPTION_NOT_FOUND` 메시지를 반환한다.
    4. 해당 option의 quantity에서 차감을 진행하고, Entity의 업데이트 메서드를 실행시킨다.
        - quantity 값이 1보다 작아지면 `IllegalArgumentException`을 발생시키고, `OPTION_QUANTITY_SIZE` 메시지를 반환한다.
    5. 해당 Entity를 DB에 저장한다.