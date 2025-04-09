package santa_cruz_alimento_backend.dto.response.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteResponseDto {
    private Long id;
    private String name;
    private Double cantidad;
    private String unidad;
    private Integer status;
}
