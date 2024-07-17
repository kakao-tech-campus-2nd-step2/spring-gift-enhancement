package gift.controller.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {

    @GetMapping
    public String index() {
        return "index";
    }
}
