package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
public class IngredienteRequestDTO {

    private String name;

    private Double cantidad;

    private Double stock;

    private String unidad;

    private Integer status;
}
