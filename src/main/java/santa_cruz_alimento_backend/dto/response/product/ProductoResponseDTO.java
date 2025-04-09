package santa_cruz_alimento_backend.dto.response.product;

import lombok.Data;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;

import java.util.List;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String name;
    private String tamaño;
    private String image_url;
    private String description;
    private Double price;
    private Integer stock;
    private Integer status;

    private Long categoryId;
    private String category_name;

    private Long businessId;
    private String business_name;

    private Long recetaId;
    private String receta_name;
    private List<DetalleRecetaResponseDto> detalleReceta;
}
