package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RecetaRequesDto {

    private String name;

    private List<DetalleRecetasRequestDto> ingredientes;
}
