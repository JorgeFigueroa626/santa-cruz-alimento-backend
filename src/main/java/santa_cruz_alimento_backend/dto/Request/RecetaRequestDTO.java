package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

import java.util.List;

@Data
public class RecetaRequestDTO {

    private String name;
    private List<RecetaIngredienteDTO> ingredientes;
}
