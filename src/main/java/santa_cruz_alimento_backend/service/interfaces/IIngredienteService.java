package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;
import santa_cruz_alimento_backend.entity.dto.IngredienteResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Ingrediente;

import java.util.List;

@Service
public interface IIngredienteService {

    Ingrediente save(Ingrediente ingrediente);

    Ingrediente getById(Long id);

    List<IngredienteResponseDTO> findAll();

    Ingrediente updateById(Long id, Ingrediente ingrediente);

    void deleteById(Long id);
}
