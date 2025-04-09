package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.response.base.BaseResponseDto;
import santa_cruz_alimento_backend.dto.response.base.DetalleBaseResponseDto;
import santa_cruz_alimento_backend.entity.model.Base;
import santa_cruz_alimento_backend.entity.model.DetalleBase;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBaseMapper {

    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "description")
    @Mapping(target = "status")
    BaseResponseDto toBaseResponseDto(Base base);
    List<BaseResponseDto> toBaseResponseDtoList(List<Base> bases);


    @Mapping(target = "id")
    @Mapping(source = "ingrediente.id", target = "ingredienteId")
    @Mapping(source = "ingrediente.name", target = "nombre_ingrediente")
    @Mapping(target = "cantidad")
    @Mapping(target = "unidad")
    DetalleBaseResponseDto toDetailBaseResponseDto(DetalleBase detalleBase);
    List<DetalleBaseResponseDto> toDetailBaseResponseDtoList(List<DetalleBase> detalleBases);
}
