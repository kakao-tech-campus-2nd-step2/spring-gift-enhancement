package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.request.MemberRequest;
import gift.dto.response.ProductResponse;
import gift.dto.request.WishRequest;
import gift.dto.response.WishResponseDto;
import gift.exception.WishNotFoundException;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;

    public WishService(WishRepository wishRepository, ProductService productService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
    }

    @Transactional
    public void save(MemberRequest memberRequest, WishRequest wishRequest){
        ProductResponse productResponseDto = productService.findByName(wishRequest.productName());

        Product product = productResponseDto.toEntity();
        Member member = memberRequest.toEntity();

        Wish newWish = new Wish.Builder()
                .member(member)
                .product(product)
                .qunatity(wishRequest.quantity())
                .build();

        wishRepository.save(newWish);
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getMemberWishesByMemberId(Long memberId){
        return wishRepository.findByMemberId(memberId)
                .stream()
                .map(WishResponseDto::from)
                .toList();

    }

    @Transactional(readOnly = true)
    public Page<WishResponseDto> getPagedMemberWishesByMemberId(Long memberId, Pageable pageable){
        Page<Wish> wishPage = wishRepository.findByMemberId(memberId,pageable);
        return wishPage.map(WishResponseDto::from);
    }

    @Transactional
    public void deleteWishByMemberIdAndId(Long memberId, Long id){
        wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        wishRepository.deleteById(id);
    }

    @Transactional
    public void updateQuantityByMemberIdAndId(Long memberId, Long id, WishRequest request){
        Wish existingWish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        existingWish.updateQuantity(request.quantity());
    }
}
