package gift.controller.dto;

import jakarta.validation.constraints.NotNull;

public class OptionResponse {
    @NotNull
    Long id;
    @NotNull
    String name;
    @NotNull
    int quantity;

}
