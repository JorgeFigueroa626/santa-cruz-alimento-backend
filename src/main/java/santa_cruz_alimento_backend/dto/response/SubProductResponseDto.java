package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SubProductResponseDto {

    private Long id;

    private String name;

    private Integer status;

    private List<DetalleBaseResponseDto> detalleBases;
}
