package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IIngredienteService {

    Ingrediente save(IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException;

    Ingrediente getById(Long id) throws ExceptionNotFoundException;

    List<IngredientesResponseDto> findAll() throws ExceptionNotFoundException;

    Ingrediente updateById(Long id, IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
