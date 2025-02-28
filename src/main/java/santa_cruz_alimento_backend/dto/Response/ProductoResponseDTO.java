package santa_cruz_alimento_backend.dto.Response;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private Long categoryId;
    private Long businessId;
    private String business_name;
    private Long recetaId;
    private String receta_name;
}
