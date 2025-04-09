package santa_cruz_alimento_backend.dto.request.ingredient;

import lombok.Data;

@Data
public class IngredienteRequestDTO {

    private String name;

    private Double cantidad;

    private Double stock;

    private String unidad;

    private Integer status;
}
