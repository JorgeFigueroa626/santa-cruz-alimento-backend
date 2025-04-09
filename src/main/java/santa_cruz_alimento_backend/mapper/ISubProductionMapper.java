package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.response.category.DetalleSubProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.category.SubProduccionResponseDto;
import santa_cruz_alimento_backend.entity.model.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISubProductionMapper {

    @Mapping(target = "id")
    @Mapping(target = "solicitud_produccion")
    @Mapping(target = "producido")
    @Mapping(target = "comentario")
    @Mapping(source = "fechaProduccion", target = "fecha_produccion")
    @Mapping(target = "status")
    @Mapping(source = "category.id", target = "categoriaId")
    @Mapping(source = "category.name", target = "categoria_nombre")
    SubProduccionResponseDto toSubProductionResponseDto(SubProduccion subProduccion);
    List<SubProduccionResponseDto> toSubProductionResponseDtoList(List<SubProduccion> subProduccions);

    @Mapping(target = "id")
    @Mapping(source = "ingrediente.id", target = "ingredienteId")
    @Mapping(source = "ingrediente.name", target = "nombre_ingrediente")
    @Mapping(target = "cantidad")
    @Mapping(target = "unidad")
    DetalleSubProduccionResponseDto toSubProductionDetailResponseDto(DetalleSubProduccion detalleSubProduccion);
    List<DetalleSubProduccionResponseDto> toSubProductionDetailResponseDtoList(List<DetalleSubProduccion> detalleSubProduccions);
}
