package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.RecetaRequesDto;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.dto.response.RecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public interface IRecetaService {

    boolean addReceta(Receta receta) throws IOException;


    Receta createReceta(RecetaRequesDto dto) throws ExceptionNotFoundException;

    List<RecetaResponseDto> findAll();

    //List<Ingrediente> getIngredientesByNameReceta(String receta) throws ExceptionNotFoundException;

    List<IngredientesResponseDto> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException;

    RecetaResponseDto getByRecetaId(Long id) throws ExceptionNotFoundException;

    Receta updateById(Long id, RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
