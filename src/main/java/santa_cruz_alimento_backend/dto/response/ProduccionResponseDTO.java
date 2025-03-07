package santa_cruz_alimento_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProduccionResponseDTO {
    private Long id;
    private Double solicitud_proudcion;
    private Integer producido;
    private String  comentario;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;
    private Long productoId;
    private String producto_name;
    private List<DetalleProduccionResponseDto> detalleProduccions;
}
