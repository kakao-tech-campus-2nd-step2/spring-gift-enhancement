package gift.api.wishlist;

import gift.global.LoginMember;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping()
    public ResponseEntity<List<Wish>> getItems(@LoginMember Long memberId, Pageable pageable) {
        return ResponseEntity.ok().body(wishService.getItems(memberId, pageable));
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody @Valid WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.add(memberId, wishRequest);
        return ResponseEntity.created(URI.create("/api/wishes/" + memberId)).build();
    }

    @PutMapping()
    public ResponseEntity<Void> update(@RequestBody @Valid WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.update(memberId, wishRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody @Valid WishRequest wishRequest, @LoginMember Long memberId) {
        wishService.delete(memberId, wishRequest);
        return ResponseEntity.noContent().build();
    }
}
