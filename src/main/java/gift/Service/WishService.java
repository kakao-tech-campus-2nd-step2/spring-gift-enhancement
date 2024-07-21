package gift.Service;

import gift.Exception.ProductNotFoundException;
import gift.Exception.WishNotFoundException;
import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;


    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }



    @Transactional
    public void addWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(()->new ProductNotFoundException("매칭되는 상품이 없습니다."));
        Wish wish = new Wish(member, product, requestWishDTO.getCount());
        wishRepository.save(wish);
    }

    @Transactional(readOnly = true)
    public Page<Wish> getWishList(Member member, Pageable pageable) {
        Page<Wish> wishListPage= wishRepository.findByMember(member,pageable);
        return wishListPage;
    }

    @Transactional(readOnly = true)
    public List<ResponseWishDTO> getWish(Member member) {
        List<Wish> wishList = wishRepository.findWishListByMember(member);
        List<ResponseWishDTO> responseWishDTOList = new ArrayList<>();
        for(Wish wish : wishList){
            responseWishDTOList.add(new ResponseWishDTO(wish.getProduct().getName(), wish.getCount()));
        }

        return responseWishDTOList;
    }

    @Transactional(readOnly = true)
    public Wish findWishByMemberAndProduct(Member member, Product product){
        Optional<Wish> wish= wishRepository.findByMemberAndProduct(member, product);
        return wish.orElseThrow(()->new WishNotFoundException("매칭되는 wish가 없습니다"));
    }

    @Transactional
    public List<ResponseWishDTO> editWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("매칭되는 물건이 없습니다."));
        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new WishNotFoundException("매칭되는 wish가 없습니다"));
        wish.setCount(requestWishDTO.getCount());
        return getWish(member);
    }

    @Transactional
    public List<ResponseWishDTO> deleteWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("매칭되는 물건이 없습니다."));
        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new WishNotFoundException("매칭되는 wish가 없습니다"));
        wishRepository.deleteById(wish.getId());
        return getWish(member);
    }

}