package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

@Data
public class DetalleProduccionResponseDto {
    private Long id;
    private String nombre_ingrediente;
    private Double cantidad;
    private String unidad;
}
