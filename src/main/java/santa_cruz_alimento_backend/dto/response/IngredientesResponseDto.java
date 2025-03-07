package santa_cruz_alimento_backend.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientesResponseDto {
    private Long id;
    private String name;
    private Double cantidad;
    private String unidad;



}

