package gift.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.ValidationException;

@Embeddable
public class Name {

    private String name;

    protected Name() {
    }

    public Name(String name) {
        validateName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("이름을 입력해주세요.");
        }
        if (name.length() < 1 || name.length() > 15) {
            throw new ValidationException("1자 ~ 15자까지 가능합니다.");
        }
        if (name.contains("카카오")) {
            throw new ValidationException("카카오가 포함된 문구는 현재 사용 할 수 없습니다.");
        }
        if (!name.matches("^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$")) {
            throw new ValidationException("사용불가한 특수 문자가 포함되어 있습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}