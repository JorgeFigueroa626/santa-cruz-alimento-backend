package santa_cruz_alimento_backend.dto.request.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;

import java.sql.Timestamp;

@Data
public class SubProduccionRequestDto {

    private Double producido;

    private String comentario;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion;

    @Enumerated(EnumType.STRING)
    private ReplyProduction status;

    //private Long categoryId;
}
