package santa_cruz_alimento_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.entity.model.DetalleProduccion;
import santa_cruz_alimento_backend.entity.model.Product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProduccionRequesDto {

    private Double solicitud_produccion;

    private Integer producido;

    private String comentario;

    @Column(name = "fecha_produccion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fechaProduccion;


    private List<DetalleProduccionRequestDto> ingredientes;
}
