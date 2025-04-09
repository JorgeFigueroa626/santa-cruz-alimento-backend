package santa_cruz_alimento_backend.dto.response.production;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.Base;
import santa_cruz_alimento_backend.entity.model.Receta;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProductionRecipeBaseResponseDto {
    private Long id;                        //El id es de Produccion
    private Double solicitud_produccion;  //La solicitud_produccion es del Produccion
    private Double producido;            //La producido es del Produccion
    private String  comentario;         //La comentario es del Produccion
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_produccion; //La fecha_produccion es del Produccion
    private String status;              //La status es del Produccion
    private Long productoId;            //El productoId es de Produccion
    private String producto_name;       //La producto_name es de Produccion
    private Long recetaId;              //La recetaId es del Producto
    private String receta_nombre;       //La receta_nombre es del Producto
    private Long baseId;                //La cantidad es del detalleProduccion
    private String base_nombre;         //La cantidad es del detalleProduccion
    private Double cantidad;            //La cantidad es del detalleProduccion
    private String unidad;              //La unidad es del detalleProduccion
}
