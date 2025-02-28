package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Response.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IProduccionService {

    Produccion calcularProduccion(Long productoId, double produccion_total) throws ExceptionNotFoundException;

    Produccion registrarProduccion(Long productoId, double solicitud_producion) throws ExceptionNotFoundException;

    Produccion editarProduccionById(Long produccionId, Produccion produccionActualizada) throws ExceptionNotFoundException;

    ProduccionResponseDTO getByProduccionId(Long id) throws ExceptionNotFoundException;

    List<ProduccionResponseDTO> findAllProduccions() throws ExceptionNotFoundException;
}
