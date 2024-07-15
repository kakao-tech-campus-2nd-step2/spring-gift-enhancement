package gift.controller;

import gift.dto.TokenDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/wish")
@Controller
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping()
    public void save(@RequestBody WishRequestDto wishRequestDto) {
        wishService.save(wishRequestDto.getProductId(), wishRequestDto.getTokenValue());
    }

    @GetMapping()
    public List<WishResponseDto> getAll(@RequestParam TokenDto token) {
        return wishService.getAll(token);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @RequestParam TokenDto tokenDto) throws IllegalAccessException {
        wishService.delete(id, tokenDto);
    }

    @GetMapping("/wishes")
    public Page<WishResponseDto> getWishes(Pageable pageable) {
        return wishService.getWishes(pageable);
    }

}
