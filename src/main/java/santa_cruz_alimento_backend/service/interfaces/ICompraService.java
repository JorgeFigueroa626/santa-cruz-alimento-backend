package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.purchase.CompraRequestDto;
import santa_cruz_alimento_backend.dto.response.purchase.CompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface ICompraService {

    Compra createCompra(CompraRequestDto dto) throws ExceptionNotFoundException;

    List<CompraResponseDto> findAll() throws ExceptionNotFoundException;

    CompraResponseDto getByCompraId(Long id) throws ExceptionNotFoundException;

    List<Compra> obtenerComprasPorIngrediente(Long id) throws ExceptionNotFoundException;

    boolean updateById(Long id, CompraRequestDto requestDto) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
