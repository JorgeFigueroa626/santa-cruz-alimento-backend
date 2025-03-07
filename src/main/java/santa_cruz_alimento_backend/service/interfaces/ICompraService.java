package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.CompraRequestDto;
import santa_cruz_alimento_backend.dto.response.CompraResponseDto;
import santa_cruz_alimento_backend.dto.response.DetalleCompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface ICompraService {

    Compra createCompra(CompraRequestDto dto) throws ExceptionNotFoundException;

    List<CompraResponseDto> findAll() throws ExceptionNotFoundException;

    //List<Ingrediente> getIngredientesByNameReceta(String receta);

    //List<IngredienteDTO> getRecetaByNombre(String nombreReceta);

    CompraResponseDto getByCompraId(Long id) throws ExceptionNotFoundException;

    List<Compra> obtenerComprasPorIngrediente(Long id) throws ExceptionNotFoundException;

    boolean updateById(Long id, Compra compra) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
