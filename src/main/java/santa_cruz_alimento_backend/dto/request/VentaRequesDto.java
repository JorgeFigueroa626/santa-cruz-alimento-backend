package santa_cruz_alimento_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class VentaRequesDto {

    private Long usuarioId; // Nuevo campo para registrar la venta con usuario

    @Column(name = "fecha_venta")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fechaVenta;

    private List<DetalleVentasRequestDto> detalles;
}
