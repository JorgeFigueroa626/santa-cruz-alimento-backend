package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.recipe.RecetaRequesDto;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredienteResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.RecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IRecetaService {

    ///
    Receta createReceta(RecetaRequesDto dto) throws ExceptionNotFoundException;

    List<RecetaResponseDto> findAll();

    List<IngredienteResponseDto> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException;

    RecetaResponseDto getByRecetaId(Long id) throws ExceptionNotFoundException;

    Receta updateById(Long id, RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
