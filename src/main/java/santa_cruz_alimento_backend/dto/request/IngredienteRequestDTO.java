package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

@Data
public class IngredienteRequestDTO {
    private String name;

    private Double cantidad;

    private String unidad;
}
