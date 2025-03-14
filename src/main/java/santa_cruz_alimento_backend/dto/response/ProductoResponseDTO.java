package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String name;
    private String image_url;
    private String description;
    private Double price;
    private Integer stock;
    private Integer status;
    private Long categoryId;
    private Long businessId;
    private String business_name;
    private Long recetaId;
    private String receta_name;
}
