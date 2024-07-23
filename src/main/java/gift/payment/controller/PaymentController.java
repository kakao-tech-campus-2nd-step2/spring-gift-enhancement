package gift.payment.presentation;

import gift.payment.application.PaymentService;
import gift.util.CommonResponse;
import gift.util.annotation.JwtAuthenticated;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process/{wishListId}")
    @JwtAuthenticated
    public ResponseEntity<?> processPayment(@PathVariable Long wishListId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.valueOf(authentication.getName());

        paymentService.processPayment(userId, wishListId);
        return ResponseEntity.ok(new CommonResponse<>(
                null, "결제 완료", true
        ));
    }

}
