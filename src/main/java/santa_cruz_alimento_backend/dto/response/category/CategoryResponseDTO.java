package santa_cruz_alimento_backend.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private Integer status;
}
