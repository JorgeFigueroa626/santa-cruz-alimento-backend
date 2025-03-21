package santa_cruz_alimento_backend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubProductRequestDto {

    private String name;

    private Integer status;

    private List<DetalleBaseRequestDto> detallesBases;
}
