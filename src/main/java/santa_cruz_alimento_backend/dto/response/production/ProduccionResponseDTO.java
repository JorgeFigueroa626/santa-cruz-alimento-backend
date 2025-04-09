package santa_cruz_alimento_backend.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import santa_cruz_alimento_backend.dto.response.product.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProduccionResponseDTO {
    private Long id;
    private Double solicitud_produccion;
    private Double producido;
    private String  comentario;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;
    private String status;
    private Long productoId;
    private String producto_name;
/*    private Long categoriaId;
    private String categoria_name;*/
    private List<DetalleProduccionResponseDto> detalleProduccions;
}
