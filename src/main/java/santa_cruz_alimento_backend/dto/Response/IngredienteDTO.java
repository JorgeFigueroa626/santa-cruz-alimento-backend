package santa_cruz_alimento_backend.dto.Response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteDTO {

    private Long id;
    private String name;
    private String unidad;
    private Double cantidad;
}
