package dashboard.dto.request;

public record ProductUpdateRequest(
    String name,
    Double price,
    Integer quantity,
    String category,
    String brand
) {
}
