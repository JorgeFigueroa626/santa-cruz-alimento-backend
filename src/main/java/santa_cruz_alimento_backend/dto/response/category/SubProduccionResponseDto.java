package santa_cruz_alimento_backend.dto.response.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SubProduccionResponseDto {

    private Long id;
    private Double solicitud_produccion;
    private Double producido;
    private String  comentario;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;
    private String status;
    private Long categoriaId;
    private String categoria_nombre;
    private List<DetalleSubProduccionResponseDto> detalleSubProduccions;
}
