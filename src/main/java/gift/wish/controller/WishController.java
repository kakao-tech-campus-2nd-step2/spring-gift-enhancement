package gift.wish.controller;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import gift.user.dto.UserDto;
import gift.wish.dto.WishDto;
import gift.security.LoginMember;
import gift.wish.service.WishService;
import gift.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishes")
public class WishController {

  private final WishService wishService;

  @Autowired
  public WishController(WishService wishService) {
    this.wishService = wishService;
  }

  @GetMapping
  public ResponseEntity<Page<WishDto>> getAllWishes(
      @LoginMember User user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String direction) {

    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    Page<WishDto> wishes = wishService.getWishesByMemberEmail(user.getEmail(), pageable);
    return ResponseEntity.ok(wishes);
  }

  @PostMapping
  public ResponseEntity<WishDto> addWish(@RequestBody WishDto wishDto, @LoginMember User member) {
    wishDto.setUser(
        new UserDto(member.getId(), member.getEmail(), member.getPassword(), member.getRole()));
    WishDto createdWish = wishService.addWish(wishDto);
    return ResponseEntity.ok(createdWish);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> removeWish(@PathVariable Long productId, @LoginMember User member) {
    wishService.removeWish(member.getEmail(), productId);
    return ResponseEntity.ok().build();
  }
}
