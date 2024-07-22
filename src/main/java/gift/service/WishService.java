package gift.service;

import gift.dto.WishRequest;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Page<Wish> getWishes(Pageable pageable) {
        return wishRepository.findAll(pageable);
    }

    @Autowired
    public WishService(WishRepository wishRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<Wish> getWishes(String token) {
        String email = extractEmailFromToken(token);
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return wishRepository.findByMember(member.get());
        }
        throw new RuntimeException("회원을 찾을 수 없습니다.");
    }

    public Wish addWish(String token, WishRequest wishRequest) {
        String email = extractEmailFromToken(token);
        Optional<Member> member = memberRepository.findByEmail(email);
        Optional<Product> product = productRepository.findById(wishRequest.getProductId());
        if (member.isPresent() && product.isPresent()) {
            Wish wish = new Wish(member.get(), product.get());
            return wishRepository.save(wish);
        }
        throw new RuntimeException("회원을 찾을 수 없거나 제품을 찾을 수 없습니다.");
    }

    public void removeWish(String token, Long productId) {
        String email = extractEmailFromToken(token);
        Optional<Member> member = memberRepository.findByEmail(email);
        Optional<Product> product = productRepository.findById(productId);
        if (member.isPresent() && product.isPresent()) {
            wishRepository.deleteByMemberAndProduct(member.get(), product.get());
        } else {
            throw new RuntimeException("회원을 찾을 수 없거나 제품을 찾을 수 없습니다.");
        }
    }

    private String extractEmailFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String base64Credentials = token.substring(7);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            return credentials.split(":")[0];
        }
        throw new RuntimeException("잘못된 토큰입니다.");
    }
}