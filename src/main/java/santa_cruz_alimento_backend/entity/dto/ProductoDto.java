package santa_cruz_alimento_backend.entity.dto;

import lombok.Data;

@Data
public class ProductoDto {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer production;
    private Long categoryId;
    private Long businessId;
    private String business_name;
    private Long recetaId;
    private String receta_name;
}
