package gift.service;

import gift.dto.MemberDto;
import gift.dto.WishDto;
import gift.model.member.Member;
import gift.model.wish.Wish;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishListService {
    private final WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    public WishListService(WishRepository wishRepository){
        this.wishRepository = wishRepository;
    }

    public Page<Wish> getAllWishes(MemberDto memberDto, Pageable pageable) {
        Optional<Member> member = memberRepository.findByEmail(memberDto.email());
        if(!member.isPresent()){
            throw new RuntimeException("error");
        }
        return wishRepository.findAllByMemberId(member.get().getId(), pageable);
    }

    public void insertWish(WishDto wishDto) {
        Wish wish = new Wish(wishDto.getProduct(),wishDto.getMember(),wishDto.getAmount());
        wishRepository.save(wish);
    }

    public void deleteWish(Long productId) {
        wishRepository.deleteById(productId);
    }

    public void updateWish(Long id, WishDto wishDto){
        Wish targetWish = wishRepository.findById(id).get();
        Wish newWish = new Wish(wishDto.getProduct(),wishDto.getMember(),wishDto.getAmount());
        targetWish.updateWish(newWish);
        wishRepository.save(targetWish);
    }
}
