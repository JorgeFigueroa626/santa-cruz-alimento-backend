package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CompraDTO {

    private Timestamp fecha_compra;

    private Double total;

    private List<DetalleCompraDTO> detalleCompras;
}
