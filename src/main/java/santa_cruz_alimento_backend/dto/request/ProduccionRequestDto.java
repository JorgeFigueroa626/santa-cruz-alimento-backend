package santa_cruz_alimento_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProduccionRequestDto {

    //private Double solicitud_produccion;

    private Integer producido;

    private String comentario;

    //@JsonFormat(pattern = "yyyy-MM-dd")
    //private Timestamp fecha_produccion;

    private Long productoId;

}
