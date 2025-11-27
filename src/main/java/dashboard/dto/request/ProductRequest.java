package dashboard.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotBlank(message = "O nome é obrigatório")
    String name,

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    Double price,

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade deve ser maior ou igual a zero")
    Integer quantity,

    @NotBlank(message = "A categoria é obrigatória")
    String category,

    @NotBlank(message = "A marca é obrigatória")
    String brand
) {
}
