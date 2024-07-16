package gift.init;

import gift.domain.User.CreateUser;
import gift.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreator {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        userService.createUser(new CreateUser("kakao1@kakao.com", "1234"));
        userService.createUser(new CreateUser("kakao2@kakao.com", "123456"));
        userService.createUser(new CreateUser("kakao3@kakao.com", "1234"));
        userService.createUser(new CreateUser("kakao4@kakao.com", "qwer1234"));
        userService.createUser(new CreateUser("kakao5@kakao.com", "qer1234"));
    }
}
