package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Data
public class RecetaRequesDto {

    private String name;

    private Integer status;

    private List<DetalleRecetasRequestDto> ingredientes;
}
