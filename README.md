# spring-gift-enhancement

## 피드백 반영
- [x] JwtConfig : JwtTokenProvider 코드를 분리하기 보다는 메서드로 호출하도록 구현
- [ ] WebConfig : 실제 사용하는 코드인지 한번 디버깅
- [x] MemberController : 설정값 한곳에 모으기
- [ ] MemberService : 사용자 토큰 사용
- [x] ProductService : 메서드 사용해서 깔끔하게 정의하기
- [x] proporties : 파일 저장 UTF-8인지 확인
  - [x] Edit Custom VM Options : -Dfile.encoding=UTF-8 추가
  - [x] Tomcat Encoding 설정 : VM options  ->  -Dfile.encoding=UTF-8 설정
  - [x] intelliJ 파일 Encoding 설정 : settings/file encoding 의 Global Encoding / Project Encoding / Default encoding for properties files 모두 UTF-8설정
- [ ] Test : 사용하는 값들 상단 변수로 등록해서 사용해보기