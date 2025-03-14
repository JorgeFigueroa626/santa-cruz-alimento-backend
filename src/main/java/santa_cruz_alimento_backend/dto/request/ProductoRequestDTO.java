package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductoRequestDTO {
    private Long id;
    private String name;
    private MultipartFile image;
    private String description;
    private Double price;
    private Integer stock;
    private Integer status;
    private Long categoryId;
    private Long businessId;
    private Long recetaId;

}
