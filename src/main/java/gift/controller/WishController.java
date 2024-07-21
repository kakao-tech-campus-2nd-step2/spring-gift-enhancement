package gift.controller;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/wish")
@Controller
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping()
    public WishRequestDto save(@RequestBody WishRequestDto wishRequestDto) {
        WishResponseDto wishResponseDto = wishService.save(wishRequestDto.getProductId(), wishRequestDto.getTokenValue());
        wishResponseDto.setHttpStatus(HttpStatus.OK);
        return wishRequestDto;
    }

    @GetMapping()
    public WishResponseDto getAll(@RequestParam("Token") String token) {
        return new WishResponseDto(wishService.getAll(token), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public WishResponseDto delete(@PathVariable("id") Long id, @RequestParam("Token") String token) throws IllegalAccessException {
        wishService.delete(id, token);
        return new WishResponseDto(HttpStatus.OK);
    }

    @GetMapping("/wishes")
    public Page<WishResponseDto> getWishes(Pageable pageable) {
        return wishService.getWishes(pageable);
    }

}
