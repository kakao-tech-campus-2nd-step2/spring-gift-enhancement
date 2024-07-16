# spring-gift-enhancement

## step0 구현 기능

- 3주차 코드 클론
- 비밀번호 암호화
  - spring security BCryptPasswordEncoder로 암호화
  - data.sql로 유저를 생성하면 db에 암호화가 안된채로 저장됨으로 UserCreator추가
    - UserCreator는 @PostConstruct를 사용한다.
    - 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다
  - 로그인시 email로 유저를 조회하고 조회된 비밀번호화 입력된 비밀번호를 대조해서 인증한다.
  - 유저 생성과 비밀번호 수정시 유저를 검증하고 비밀번호를 암호화 해서 저장한다.