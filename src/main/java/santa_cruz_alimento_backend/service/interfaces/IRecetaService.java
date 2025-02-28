package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Response.IngredienteDTO;
import santa_cruz_alimento_backend.dto.Request.RecetaRequestDTO;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public interface IRecetaService {

    boolean addReceta(Receta receta) throws IOException;


    Receta createReceta(RecetaRequestDTO dto) throws ExceptionNotFoundException;

    List<Receta> findAll();

    List<Ingrediente> getIngredientesByNameReceta(String receta) throws ExceptionNotFoundException;

    List<IngredienteDTO> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException;

    Receta getByRecetaId(Long id) throws ExceptionNotFoundException;

    Receta updateById(Long id, RecetaRequestDTO recetaRequestDTO) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
