package santa_cruz_alimento_backend.dto.response.production;

import lombok.Data;

@Data
public class ProductionDetailResponseDto {
    private Long id;
    private Long baseId;
    private String nombre_base;
    private Long ingredienteId;
    private String nombre_ingrediente;
    private Double cantidad;
    private String unidad;

}
