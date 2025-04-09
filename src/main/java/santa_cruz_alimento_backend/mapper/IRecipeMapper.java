package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.response.base.DetalleBaseResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.RecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.DetalleBase;
import santa_cruz_alimento_backend.entity.model.DetalleRecetas;
import santa_cruz_alimento_backend.entity.model.Receta;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRecipeMapper {

    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "status")
    RecetaResponseDto toRecipeResponseDto(Receta receta);
    List<RecetaResponseDto> toRecipeResponseDtoList(List<Receta> recetas);

    @Mapping(target = "id")
    @Mapping(source = "base.id", target = "baseId")
    @Mapping(source = "base.name", target = "nombre_base")// Mapeo de base.name// Mapeo de name
    @Mapping(source = "base.description", target = "description_base")// Mapeo de base.description// Mapeo de description
    @Mapping(source = "base.detalleBases", target = "detalleBases")
    DetalleRecetaResponseDto toRecipeDetailResponseDto(DetalleRecetas detalleRecetas);
    List<DetalleRecetaResponseDto> toRecipeDetailResponseDtoList(List<DetalleRecetas> detalleRecetas);

    // Mapeo de DetalleBase a DetalleBaseResponseDto
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ingredienteId", source = "ingrediente.id") // Mapeo del ingrediente ID
    @Mapping(target = "nombre_ingrediente", source = "ingrediente.name") // Mapeo del ingrediente nombre
    @Mapping(target = "cantidad", source = "cantidad")
    @Mapping(target = "unidad", source = "unidad")
    DetalleBaseResponseDto toDetalleBaseResponseDto(DetalleBase detalleBase);
    List<DetalleBaseResponseDto> toDetalleBaseResponseDtoList(List<DetalleBase> detalleBases);

}
