package santa_cruz_alimento_backend.entity.dto;

import lombok.Data;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.RecetaIngrediente;

import java.util.List;
import java.util.Set;

@Data
public class RecetaDto {

    private String name;
    private List<RecetaIngredienteDTO> ingredientes;
}
