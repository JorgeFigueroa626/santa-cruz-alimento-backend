package santa_cruz_alimento_backend.dto.request.sale;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class VentaRequesDto {

    private Long usuarioId; // Nuevo campo para registrar la venta con usuario

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_venta;

    private List<DetalleVentasRequestDto> detallesVentas;
}
