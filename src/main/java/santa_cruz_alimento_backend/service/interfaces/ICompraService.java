package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.CompraDTO;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface ICompraService {

    Compra createCompra(CompraDTO dto) throws ExceptionNotFoundException;

    List<Compra> findAll() throws ExceptionNotFoundException;

    //List<Ingrediente> getIngredientesByNameReceta(String receta);

    //List<IngredienteDTO> getRecetaByNombre(String nombreReceta);

    Compra getByCompraId(Long id) throws ExceptionNotFoundException;

    List<Compra> obtenerComprasPorIngrediente(Long id) throws ExceptionNotFoundException;

    boolean updateById(Long id, Compra compra) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
