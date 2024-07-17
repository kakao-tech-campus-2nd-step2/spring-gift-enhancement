package gift.controller;


import gift.dto.PagingRequest;
import gift.dto.PagingResponse;
import gift.model.category.CategoryResponse;
import gift.model.gift.GiftRequest;
import gift.model.gift.GiftResponse;
import gift.service.category.CategoryService;
import gift.service.gift.GiftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private final GiftService giftService;
    private final CategoryService categoryService;

    @Autowired
    public AdminController(GiftService giftService, CategoryService categoryService) {
        this.giftService = giftService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminHome(Model model, @ModelAttribute PagingRequest pagingRequest) {
        PagingResponse<GiftResponse> giftlist = giftService.getAllGifts(pagingRequest.getPage(), pagingRequest.getSize());
        model.addAttribute("giftlist", giftlist.getContent());
        return "admin";
    }

    @GetMapping("/admin/gift/create")
    public String giftCreate(Model model) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "create_form";
    }

    @PostMapping("/admin/gift/create")
    public String giftCreate(@Valid @ModelAttribute GiftRequest giftRequest) {
        giftService.addGift(giftRequest);
        return "redirect:/admin";
    }

    @GetMapping("/admin/gift/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        GiftResponse gift = giftService.getGift(id);
        model.addAttribute("gift", gift);
        return "gift_detail";
    }

    @GetMapping("/admin/gift/modify/{id}")
    public String giftModify(Model model, @PathVariable("id") Long id) {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        GiftResponse gift = giftService.getGift(id);
        model.addAttribute("gift", gift);
        return "modify_form";
    }

    @PutMapping("/admin/gift/modify/{id}")
    public String giftModify(@PathVariable("id") Long id, @ModelAttribute GiftRequest giftRequest) {
        giftService.updateGift(giftRequest, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/gift/delete/{id}")
    public String giftDelete(@PathVariable("id") Long id) {
        giftService.deleteGift(id);
        return "redirect:/admin";
    }
}

