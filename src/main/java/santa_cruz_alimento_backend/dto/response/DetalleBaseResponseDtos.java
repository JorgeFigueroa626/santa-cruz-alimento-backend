package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

@Data
public class DetalleBaseResponseDtos {
    private Long id;
    private Long ingredienteId;
    private String nombre_ingrediente;
    private Double cantidad;
    private String unidad;
}
