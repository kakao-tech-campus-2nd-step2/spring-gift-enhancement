package gift;

import gift.domain.BaseEntity;

import java.lang.reflect.Field;

public class TestUtil {
    public static void setId(Object entity, Long id) {
        try {
            Field idField = BaseEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
