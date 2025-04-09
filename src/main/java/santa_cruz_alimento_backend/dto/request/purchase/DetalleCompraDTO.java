package santa_cruz_alimento_backend.dto.request.purchase;

import lombok.Data;

@Data
public class DetalleCompraDTO {

    private Long ingredienteId;
    private Double cantidad;
    private Double precio;
    private Double total;
}
