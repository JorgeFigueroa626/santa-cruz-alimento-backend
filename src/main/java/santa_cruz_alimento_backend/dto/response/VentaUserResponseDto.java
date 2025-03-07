package santa_cruz_alimento_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class VentaUserResponseDto {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fechaVenta;
    private Double total;
    private Long usuarioId;
    private String usuarioNombre; // Para devolver el nombre del usuario
    private List<DetalleVentasResponseDto> detalles;
}
