package gift.service;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wishlist;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository,
                           MemberRepository memberRepository, MemberService memberService) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    public Page<Product> getWishlistByEmail(String email, Pageable pageable) {
        Page<Wishlist> wishlist = wishlistRepository.findByMemberEmail(email, pageable);
        return wishlist.map(Wishlist::getProduct);
    }

    public void deleteWishlistItem(String email, Long productId) {
        Wishlist wish = wishlistRepository.findByMemberEmailAndProductId(email, productId);
        if (wish != null) {
            wishlistRepository.delete(wish);
        }
    }

    public void addWishlistItem(String email, Long productId) {
        Member member = memberService.getMember(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        wishlistRepository.save(new Wishlist(member, product));
    }
}
