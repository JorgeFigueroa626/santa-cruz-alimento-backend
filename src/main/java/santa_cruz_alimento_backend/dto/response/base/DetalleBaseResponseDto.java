package santa_cruz_alimento_backend.dto.response.base;

import lombok.Data;

@Data
public class DetalleBaseResponseDto {
    private Long id;
    private Long ingredienteId;
    private String nombre_ingrediente;
    private Double cantidad;
    private String unidad;
}
