package santa_cruz_alimento_backend.dto.response;

import lombok.*;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientesResponseDto {
    private Long id;
    private String name;
    private Double cantidad;
    private Double stock;
    private String unidad;
    private ReplyStatus status;


}

