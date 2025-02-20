package santa_cruz_alimento_backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteResponseDTO {
    private Long id;
    private String name;
}
