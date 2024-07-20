package gift.service;

import gift.dto.MemberDto;
import gift.dto.WishDto;
import gift.exception.ValueNotFoundException;
import gift.model.member.Member;
import gift.model.wish.Wish;
import gift.repository.MemberRepository;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishListService {
    private final WishRepository wishRepository;
    private final MemberRepository memberRepository;

    public WishListService(WishRepository wishRepository, MemberRepository memberRepository){
        this.wishRepository = wishRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Wish> getAllWishes(MemberDto memberDto, Pageable pageable) {
        Optional<Member> member = memberRepository.findByEmail(memberDto.email());
        if(member.isEmpty()){
            throw new ValueNotFoundException("Member not exists in Database");
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
