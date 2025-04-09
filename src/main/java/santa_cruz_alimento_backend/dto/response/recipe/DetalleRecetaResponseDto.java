package santa_cruz_alimento_backend.dto.response.recipe;

import lombok.Data;
import santa_cruz_alimento_backend.dto.response.base.DetalleBaseResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetalleRecetaResponseDto {
    private Long id;
    private Long baseId;
    private String nombre_base;
    private String description_base;
    private List<DetalleBaseResponseDto> detalleBases;
}
