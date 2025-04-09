package santa_cruz_alimento_backend.dto.request.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProductionRequestDto {

    private Long productoId;

    private Double solicitud_produccion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;
}
