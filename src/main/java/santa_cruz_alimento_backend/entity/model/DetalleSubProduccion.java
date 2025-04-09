package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "detalles_sub_produccions")
public class DetalleSubProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;

    private String unidad;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id" , nullable = false)
    private Ingrediente ingrediente;

    @ManyToOne
    @JoinColumn(name = "subproduccion_id", referencedColumnName = "id", nullable = false) // Relación con Producción
    @JsonBackReference  // Evita la serialización infinita
    private SubProduccion subProduccion;
}
