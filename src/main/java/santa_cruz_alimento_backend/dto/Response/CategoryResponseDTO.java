package santa_cruz_alimento_backend.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private String name;

    public CategoryResponseDTO(Long id, String name) {
    }
}
