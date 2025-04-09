package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.production.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.ProductionRequestDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IProduccionService {

    Produccion calcularProduccion(ProductionRequestDto requestDto) throws ExceptionNotFoundException;

    Produccion solicitarProduccion(ProductionRequestDto requestDto) throws ExceptionNotFoundException;

    Produccion confirmProductionById(Long produccionId, ProduccionRequestDto requestDto) throws ExceptionNotFoundException;

    Produccion cancelProductionById(Long produccionId) throws ExceptionNotFoundException;

    ProduccionResponseDTO getByProduccionId(Long id) throws ExceptionNotFoundException;

    List<ProduccionResponseDTO> findAllProduccions() throws ExceptionNotFoundException;
}
