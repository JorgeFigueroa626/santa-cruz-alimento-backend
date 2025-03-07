package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

@Data
public class DetalleRecetasRequestDto {

    private Long ingredienteId;

    private Double cantidad;

    private String unidad;
}
