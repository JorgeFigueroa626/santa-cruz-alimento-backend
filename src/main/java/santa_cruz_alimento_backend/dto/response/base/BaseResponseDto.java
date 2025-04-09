package santa_cruz_alimento_backend.dto.response.base;

import lombok.Data;


import java.util.List;

@Data
public class BaseResponseDto {

    private Long id;

    private String name;

    private String description;

    private Integer status;

    private List<DetalleBaseResponseDto> detalleBases;
}
