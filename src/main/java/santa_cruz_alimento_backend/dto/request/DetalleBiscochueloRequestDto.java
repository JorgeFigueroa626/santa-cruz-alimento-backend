package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

@Data
public class DetalleBiscochueloRequestDto {

    private Long ingredienteId;

    private Double cantidad;

    private String unidad;
}
