package gift.repository;

import gift.domain.Member;
import gift.domain.Products;
import gift.domain.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByMember(Member member);
    boolean existsByMemberAndProduct(Member member, Products product);
    void deleteByMemberAndProduct(Member member, Products product);
    Page<Wish> findByMember(Member member, Pageable pageable);
}
