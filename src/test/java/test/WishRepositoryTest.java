package test;


import gift.repository.WishRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishRepositoryTest {
    private WishRepositoryInterface wishes;

    public WishRepositoryTest() {
    }

    @Test
    public void save() {
    }

}
