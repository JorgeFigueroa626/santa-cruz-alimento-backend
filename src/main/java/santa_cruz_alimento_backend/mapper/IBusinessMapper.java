package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import santa_cruz_alimento_backend.dto.request.business.BusinessRequestDto;
import santa_cruz_alimento_backend.dto.response.business.BusinessResponseDto;
import santa_cruz_alimento_backend.entity.model.Business;

@Mapper(componentModel = "spring")
public interface IBusinessMapper {


    BusinessResponseDto toDto(Business business);

    Business toEntity(BusinessRequestDto requestDto);
}
