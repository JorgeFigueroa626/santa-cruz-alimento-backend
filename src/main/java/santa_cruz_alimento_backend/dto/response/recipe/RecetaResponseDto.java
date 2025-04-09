package santa_cruz_alimento_backend.dto.response.recipe;


import lombok.Data;

import java.util.List;

@Data
public class RecetaResponseDto {
    private Long id;
    private String name;
    private Integer status;
    private List<DetalleRecetaResponseDto> detalleRecetas;
}
