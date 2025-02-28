package santa_cruz_alimento_backend.dto;

import lombok.Data;

@Data
public class ProduccionIngredienteDTO {
    private Long id;
    private Double produccion_total;
    private Long productoId;
    private String producto_name;
    private Integer producto_stock;
    private Long recetaId;
    private String receta_name;

}
