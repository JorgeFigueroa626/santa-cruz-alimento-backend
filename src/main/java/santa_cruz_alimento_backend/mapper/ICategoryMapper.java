package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.request.production.SubProduccionRequestDto;
import santa_cruz_alimento_backend.dto.response.category.CategoriaResponseDto;
import santa_cruz_alimento_backend.dto.response.category.DetalleSubProductoResponseDto;
import santa_cruz_alimento_backend.dto.response.category.SubProductoResponseDto;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.DetalleSubProducto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {

    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "status")
    @Mapping(target = "stock")
    CategoriaResponseDto toCategoryResponseDto(Category category);
    List<CategoriaResponseDto> toCategoryResponseDtoList(List<Category> categories);

    @Mapping(target = "id")
    @Mapping(source = "ingrediente.id", target = "ingredienteId")
    @Mapping(source = "ingrediente.name", target = "nombre_ingrediente")
    @Mapping(target = "cantidad")
    @Mapping(target = "unidad")
    DetalleSubProductoResponseDto toDetailSubProductResponseDto(DetalleSubProducto detalleSubProducto);
    List<DetalleSubProductoResponseDto> toDetailSubProductoResponseDtoList(List<DetalleSubProducto> detalleSubProductos);
}
