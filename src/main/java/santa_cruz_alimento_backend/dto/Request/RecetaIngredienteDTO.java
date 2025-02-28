package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

@Data
public class RecetaIngredienteDTO {
    private Long ingredienteId;
    private Double cantidad;
    private String unidad;
}
