package santa_cruz_alimento_backend.dto.Response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProduccionResponseDTO {
    private Long id;
    private Double solicitud_proudcion;
    private Integer producido;
    private String  comentario;
    private Timestamp fecha_produccion;
    private Long productoId;
    private String producto_name;
    //private List<ProduccionIngredienteDTO> ingredientes;
}
