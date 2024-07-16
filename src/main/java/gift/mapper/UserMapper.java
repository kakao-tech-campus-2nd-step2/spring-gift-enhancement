package gift.mapper;

import gift.domain.User.CreateUser;
import gift.domain.User.UpdateUser;
import gift.domain.User.UserSimple;
import gift.entity.UserEntity;
import gift.util.PasswordCrypto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    PasswordCrypto passwordCrypto;

    public Page<UserSimple> toSimpleList(Page<UserEntity> all) {
        List<UserSimple> simpleList = all.stream()
            .map(entity -> new UserSimple(
                entity.getEmail(),
                entity.getPassword()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(simpleList, all.getPageable(), all.getTotalElements());
    }

    public UserEntity toEntity(CreateUser create) {
        return new UserEntity(create.getEmail(), passwordCrypto.encodePassword(create.getPassword()));
    }

    public UserEntity toUpdate(UpdateUser update, UserEntity entity) {
        entity.setPassword(passwordCrypto.encodePassword(update.getPassword()));
        return entity;
    }

    public UserEntity toDelete(UserEntity entity) {
        entity.setIsDelete(1);
        return entity;
    }
}
