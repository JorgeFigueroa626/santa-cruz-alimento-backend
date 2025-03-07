package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

@Data
public class DetalleVentasRequestDto {

    private Long productoId;
    private Integer cantidad;
}
