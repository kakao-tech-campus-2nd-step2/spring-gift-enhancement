package gift.wishes;

import gift.core.domain.product.Product;
import gift.core.domain.product.ProductCategory;
import gift.core.domain.product.ProductRepository;
import gift.core.domain.user.User;
import gift.core.domain.user.UserAccount;
import gift.core.domain.user.UserRepository;
import gift.core.domain.wishes.WishesRepository;
import gift.core.domain.wishes.WishesService;
import gift.core.domain.wishes.exception.WishAlreadyExistsException;
import gift.core.domain.wishes.exception.WishNotFoundException;
import gift.wishes.service.WishesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishesServiceTests {

    @Mock
    private WishesRepository wishesRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    private WishesService wishesService;

    @BeforeEach
    public void setUp() {
        wishesService = new WishesServiceImpl(wishesRepository, productRepository, userRepository);
    }

    @Test
    public void testAddProductToWishes() {
        Long userId = 1L;
        Product product = new Product(
                1L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(1L)).thenReturn(true);
        when(wishesRepository.exists(1L, 1L)).thenReturn(false);

        wishesService.addProductToWishes(userId, product);
        verify(wishesRepository).saveWish(userId, product.id());
    }

    @Test
    @DisplayName("이미 존재하는 위시 상품을 추가 시도 테스트")
    public void testAddProductToWishesWithExistingWish() {
        Long userId = 1L;
        Product product = new Product(
                1L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );

        when(productRepository.exists(1L)).thenReturn(true);
        when(wishesRepository.exists(1L, 1L)).thenReturn(true);

        assertThrows(WishAlreadyExistsException.class, () -> wishesService.addProductToWishes(userId, product));
    }

    @Test
    public void testRemoveProductFromWishes() {
        Long userId = 1L;
        Product product = new Product(
                1L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );


        when(wishesRepository.exists(1L, 1L)).thenReturn(true);

        wishesService.removeProductFromWishes(userId, product);
        verify(wishesRepository).removeWish(userId, product.id());
    }

    @Test
    @DisplayName("존재하지 않는 위시 상품을 삭제 시도 테스트")
    public void testRemoveProductFromWishesWithNonExistingWish() {
        Long userId = 1L;
        Product product = new Product(
                1L,
                "test",
                100,
                "test.jpg",
                ProductCategory.of("test")
        );


        when(wishesRepository.exists(1L, 1L)).thenReturn(false);

        assertThrows(WishNotFoundException.class, () -> wishesService.removeProductFromWishes(userId, product));
    }
}
