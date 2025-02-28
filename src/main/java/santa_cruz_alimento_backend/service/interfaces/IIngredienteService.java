package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.Response.IngredienteResponseDTO;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IIngredienteService {

    Ingrediente save(IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException;

    Ingrediente getById(Long id) throws ExceptionNotFoundException;

    List<IngredienteResponseDTO> findAll() throws ExceptionNotFoundException;

    Ingrediente updateById(Long id, Ingrediente ingrediente) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
