package santa_cruz_alimento_backend.dto.response.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoriaResponseDto {

    private Long id;
    private String name;
    //private String tamaño;
    private Integer stock;
    private Integer status;
    private List<DetalleSubProductoResponseDto> detalleSubProductos;
}
