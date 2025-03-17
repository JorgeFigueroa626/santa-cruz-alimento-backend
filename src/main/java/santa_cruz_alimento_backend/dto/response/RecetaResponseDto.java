package santa_cruz_alimento_backend.dto.response;


import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Data
public class RecetaResponseDto {
    private Long id;
    private String name;
    private Integer status;
    private List<DetalleRecetaResponseDto> DetalleRecetas;
}
