package santa_cruz_alimento_backend.dto.request.category;

import lombok.Data;

@Data
public class DetalleSubProductRequestDto {

    private Long ingredienteId;

    private Double cantidad;

    private String unidad;
}
