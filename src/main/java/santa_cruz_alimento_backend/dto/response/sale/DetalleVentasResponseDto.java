package santa_cruz_alimento_backend.dto.response.sale;

import lombok.Data;

@Data
public class DetalleVentasResponseDto {

    private Long id;
    private String nombre_producto;
    private String image_producto;
    private Integer cantidad;
    private Double precio_unitario;
    private Double sub_total;
}
