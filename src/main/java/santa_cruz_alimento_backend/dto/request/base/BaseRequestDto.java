package santa_cruz_alimento_backend.dto.request.base;

import lombok.Data;

import java.util.List;

@Data
public class BaseRequestDto {

    private String name;

    private String description;

    private Integer status;

    private List<DetalleBaseRequestDto> detalleBases;
}
