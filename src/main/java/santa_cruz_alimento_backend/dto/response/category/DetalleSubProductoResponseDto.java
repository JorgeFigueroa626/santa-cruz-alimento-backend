package santa_cruz_alimento_backend.dto.response.category;

import lombok.Data;

@Data
public class DetalleSubProductoResponseDto {
    private Long id;
    private Long ingredienteId;
    private String nombre_ingrediente;
    private Double cantidad;
    private String unidad;
}
