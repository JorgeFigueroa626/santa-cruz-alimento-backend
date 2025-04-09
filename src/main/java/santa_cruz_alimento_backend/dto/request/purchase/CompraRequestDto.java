package santa_cruz_alimento_backend.dto.request.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CompraRequestDto {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_compra;

    private Double total;

    private List<DetalleCompraDTO> detallesCompras;
}
