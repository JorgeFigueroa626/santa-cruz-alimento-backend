package santa_cruz_alimento_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Produccion;

@Data
public class DetalleProduccionRequestDto {

    private Long ingredienteId;

    private Double cantidad;

    private String unidad;
}
