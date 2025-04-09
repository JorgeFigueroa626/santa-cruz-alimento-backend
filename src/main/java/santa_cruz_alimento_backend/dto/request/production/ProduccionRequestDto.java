package santa_cruz_alimento_backend.dto.request.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProduccionRequestDto {

    //private Double solicitud_produccion;

    private Double producido;

    private String comentario;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;

    @Enumerated(EnumType.STRING)
    private ReplyProduction status;

    private Long productoId;

}
