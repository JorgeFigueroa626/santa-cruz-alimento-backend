package santa_cruz_alimento_backend.dto.request.recipe;

import lombok.Data;

import java.util.List;

@Data
public class RecetaRequesDto {

    private String name;

    private Integer status;

    private List<DetalleRecetasRequestDto> detalleRecetas;
    //private List<DetalleRecetasRequestDto> detallesRecetas;
}
