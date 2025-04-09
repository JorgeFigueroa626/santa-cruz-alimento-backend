package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ingredient.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IIngredienteService {

    IngredientesResponseDto save(IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException;

    Ingrediente getById(Long id) throws ExceptionNotFoundException;

    List<IngredientesResponseDto> findAll() throws ExceptionNotFoundException;

    IngredientesResponseDto updateById(Long id, IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
