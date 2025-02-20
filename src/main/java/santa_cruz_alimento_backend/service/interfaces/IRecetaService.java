package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;
import santa_cruz_alimento_backend.entity.dto.RecetaDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Receta;

import java.io.IOException;
import java.util.List;

@Service
public interface IRecetaService {

    boolean addReceta(Receta receta) throws IOException;

    Receta createReceta(RecetaDto dto);

    List<Receta> findAll();

    List<Ingrediente> getIngredientesByNameReceta(String receta);

    List<IngredienteDTO> getRecetaByNombre(String nombreReceta);

    Receta getByRecetaId(Long id);

    boolean updateById(Long id, Receta receta);

    void deleteById(Long id);
}
