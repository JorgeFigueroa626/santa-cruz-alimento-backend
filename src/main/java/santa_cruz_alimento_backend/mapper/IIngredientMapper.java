package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import santa_cruz_alimento_backend.dto.request.ingredient.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;

@Mapper(componentModel = "spring")
public interface IIngredientMapper {

    IngredientesResponseDto toResponseDto(Ingrediente ingrediente);

    Ingrediente toRequestDto(IngredienteRequestDTO requestDTO);
}
