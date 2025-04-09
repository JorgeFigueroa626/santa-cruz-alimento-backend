package santa_cruz_alimento_backend.dto.request.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequestDto {

    private String name;

    private Integer status;

    private Double stock;

    private List<DetalleSubProductRequestDto> detalleSubProductRequestDto;
}
