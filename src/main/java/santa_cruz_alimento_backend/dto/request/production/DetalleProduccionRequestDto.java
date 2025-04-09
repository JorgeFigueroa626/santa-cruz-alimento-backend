package santa_cruz_alimento_backend.dto.request.production;

import lombok.Data;

@Data
public class DetalleProduccionRequestDto {

    private Long ingredienteId;

    private Double cantidad;

    private String unidad;
}
