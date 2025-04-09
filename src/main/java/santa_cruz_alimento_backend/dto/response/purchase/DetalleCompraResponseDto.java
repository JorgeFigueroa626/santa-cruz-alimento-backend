package santa_cruz_alimento_backend.dto.response.purchase;

import lombok.Data;

@Data
public class DetalleCompraResponseDto {
    private Long id;
    private String nombre_ingrediente;
    private Double cantidad;
    private Double pricio_unitario;
    private String unidad;
    private Double sub_total;
}
