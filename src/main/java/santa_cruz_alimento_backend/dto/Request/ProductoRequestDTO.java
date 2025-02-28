package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

@Data
public class ProductoRequestDTO {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Long categoryId;
    private Long businessId;
    private Long recetaId;
}
