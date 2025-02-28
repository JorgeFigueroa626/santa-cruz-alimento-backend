package santa_cruz_alimento_backend.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Long category_id;

    private Long business_id;
}
