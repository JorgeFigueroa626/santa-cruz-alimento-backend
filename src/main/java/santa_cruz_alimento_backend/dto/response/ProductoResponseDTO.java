package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

import java.util.List;

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
    private String category_name;

    /*private Long subproductId;
    private String subproduct_name;
    private List<DetalleBaseResponseDto> detallesBases;*/

    private Long businessId;
    private String business_name;

    private Long recetaId;
    private String receta_name;
    private List<DetalleRecetaResponseDto> detalleReceta;
}
