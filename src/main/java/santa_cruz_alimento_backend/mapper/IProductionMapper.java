package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.response.product.ProductoResponseDTO;
import santa_cruz_alimento_backend.dto.response.production.DetalleProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.DetalleProduccion;
import santa_cruz_alimento_backend.entity.model.Produccion;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductionMapper {

    @Mapping(target = "id")
    @Mapping(target = "solicitud_produccion")
    @Mapping(target = "producido")
    @Mapping(target = "comentario")
    @Mapping(source = "fechaProduccion", target = "fecha_produccion")
    @Mapping(target = "status")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.name", target = "producto_name")
    ProduccionResponseDTO toProductionResponseDto(Produccion produccion);
    List<ProduccionResponseDTO> toProductionResponseDtoList(List<Produccion> produccions);

    @Mapping(target = "id")
    @Mapping(source = "nombreBase", target = "nombre_base")
    @Mapping(source = "ingrediente.name", target = "nombre_ingrediente")
    @Mapping(target = "cantidad")
    @Mapping(target = "unidad")
    DetalleProduccionResponseDto toProductionDetailResponseDto(DetalleProduccion detalleProduccion);
    List<DetalleProduccionResponseDto> toProductionDetailResponseDtoList(List<DetalleProduccion> detalleProduccions);
}
