package gift.service;

import gift.constants.Messages;
import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.dto.request.MemberRequestDto;
import gift.dto.response.ProductResponseDto;
import gift.dto.request.WishRequestDto;
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
    public void save(MemberRequestDto memberRequestDto, WishRequestDto wishRequestDto){
        ProductResponseDto productResponseDto = productService.findByName(wishRequestDto.getProductName());

        Product product = productResponseDto.toEntity();
        Member member = memberRequestDto.toEntity();

        Wish newWish = new Wish.Builder()
                .member(member)
                .product(product)
                .qunatity(wishRequestDto.getQuantity())
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
        Wish wish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));
        wish.detachFromMemberAndProduct();
        wishRepository.deleteById(id);
    }

    @Transactional
    public void updateQuantityByMemberIdAndId(Long memberId, Long id, WishRequestDto request){
        Wish existingWish = wishRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(()-> new WishNotFoundException(Messages.NOT_FOUND_WISH));

        existingWish.updateQuantity(request.getQuantity());
    }
}
