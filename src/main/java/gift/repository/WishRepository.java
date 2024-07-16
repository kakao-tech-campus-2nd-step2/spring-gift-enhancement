package gift.repository;


import gift.entity.Member;
import gift.entity.Wish;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findAllByMember(Member member, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Wish w WHERE w.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}