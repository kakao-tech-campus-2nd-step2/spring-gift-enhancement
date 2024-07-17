package gift.user.infrastructure.persistence;

import gift.core.BaseEntity;
import gift.core.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "`user`")
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    public UserEntity() {
    }

    public UserEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public UserEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserEntity from(User user) {
        return new UserEntity(user.id(), user.name());
    }
}
