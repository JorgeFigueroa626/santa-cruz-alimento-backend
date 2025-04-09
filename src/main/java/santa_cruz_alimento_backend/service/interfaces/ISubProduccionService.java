package santa_cruz_alimento_backend.service.interfaces;


import santa_cruz_alimento_backend.dto.request.production.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.ProductionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.SubProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.SubProductionRequestDto;
import santa_cruz_alimento_backend.dto.response.category.SubProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.entity.model.SubProduccion;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

public interface ISubProduccionService {

    SubProduccion calcularSubProduction(SubProductionRequestDto requestDto) throws ExceptionNotFoundException;

    SubProduccion solicitarSubProduction(SubProductionRequestDto requestDto) throws ExceptionNotFoundException;

    SubProduccion confirmSubProductionById(Long produccionId, SubProduccionRequestDto requestDto) throws ExceptionNotFoundException;

    SubProduccion cancelProductionById(Long produccionId) throws ExceptionNotFoundException;

    SubProduccionResponseDto getBySubProduccionId(Long id) throws ExceptionNotFoundException;

    List<SubProduccionResponseDto> findAllSubProduccions() throws ExceptionNotFoundException;
}
