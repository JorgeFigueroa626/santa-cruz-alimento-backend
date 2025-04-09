package santa_cruz_alimento_backend.dto.request.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductoRequestDTO {
    private String name;
    private MultipartFile image;
    private String tamaño;
    private String description;
    private Double price;
    private Integer stock;
    private Integer status;
    private Long categoryId;
    private Long subproductId;
    private Long businessId;
    private Long recetaId;

}
