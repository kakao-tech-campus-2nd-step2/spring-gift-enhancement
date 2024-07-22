package gift.service;

import gift.controller.MenuController;
import gift.domain.*;
import gift.repository.CategoryRepository;
import gift.repository.MenuRepository;
import gift.repository.OptionRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public MenuService(MenuRepository menuRepository, CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public MenuResponse save(MenuRequest request) {
        Menu menu = MapMenuRequestToMenu(request);
        return MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public List<MenuResponse> findall(
            Pageable pageable
    ) {
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.stream()
                .map(this::MapMenuToMenuResponse)
                .collect(Collectors.toList());
    }

    public Menu findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴 정보가 없습니다."));
        return menu;
    }

    public MenuResponse update(Long id, MenuRequest menuRequest) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴 정보가 없습니다."));

        menu.update(new Menu(id, menuRequest,
                categoryRepository.findById(menuRequest.categoryId())
                        .orElseThrow(() -> new NoSuchElementException("해당하는 카테고리가 존재하지 않습니다."))));
        return MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    public Set<Option> getOptions(Long id) {
        return menuRepository.getOptionsById(id);
    }

    public void addOptions(Long id, OptionRequest optionRequest){
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당하는 메뉴가 존재하지 않습니다."));
        Set<Option> options = menu.getOptions();
        if(options.contains(mapOptionRequestToOption(optionRequest))){
            throw new IllegalArgumentException("중복된 옵션입니다.");
        }
        Option option = optionRepository.save(mapOptionRequestToOption(optionRequest));
        options.add(option);
        menuRepository.save(menu);
    }

    public Menu MapMenuRequestToMenu(MenuRequest menuRequest) {
        return new Menu(menuRequest.name(), menuRequest.price(), menuRequest.imageUrl(),categoryRepository.findById(menuRequest.categoryId()).get(),menuRequest.options());
    }

    public MenuResponse MapMenuToMenuResponse(Menu menu) {
        return new MenuResponse(menu.getId(), menu.getName(), menu.getPrice(), menu.getImageUrl(), menu.getCategory(),menu.getOptions());
    }

    public Option mapOptionRequestToOption(OptionRequest optionRequest){
        return new Option(optionRequest.id(),optionRequest.name(),optionRequest.quantity());
    }

}
