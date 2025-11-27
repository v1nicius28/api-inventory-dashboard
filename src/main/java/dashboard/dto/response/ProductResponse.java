package dashboard.dto.response;

public record ProductResponse(
    String id,
    String name,
    Double price,
    Integer quantity,
    String category,
    String brand
) {
}
