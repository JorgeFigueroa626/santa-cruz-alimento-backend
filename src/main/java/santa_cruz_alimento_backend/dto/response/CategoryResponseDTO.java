package santa_cruz_alimento_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private Integer status;
}
