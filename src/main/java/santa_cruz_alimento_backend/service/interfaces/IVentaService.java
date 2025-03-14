package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.VentaRequesDto;
import santa_cruz_alimento_backend.dto.response.VentaResponseDto;
import santa_cruz_alimento_backend.dto.response.VentaUserResponseDto;
import santa_cruz_alimento_backend.entity.model.Venta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IVentaService {

    Venta registrarVenta(VentaRequesDto ventaDTO) throws ExceptionNotFoundException;

    VentaResponseDto getByVentaId(Long id) throws ExceptionNotFoundException;

    List<VentaResponseDto> findAll() throws ExceptionNotFoundException;

    List<VentaUserResponseDto> findAllVentasByUsuarioId(Long userId) throws ExceptionNotFoundException;
}
