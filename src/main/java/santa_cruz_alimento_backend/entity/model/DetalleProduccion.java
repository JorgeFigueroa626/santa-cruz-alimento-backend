package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "detalles_produccions")
public class DetalleProduccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_base")
    private String nombreBase;

    private Double cantidad;

    private String unidad;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id" , nullable = false)
    private Ingrediente ingrediente;

    @ManyToOne
    @JoinColumn(name = "produccion_id", referencedColumnName = "id", nullable = false) // Relación con Producción
    @JsonBackReference  // Evita la serialización infinita
    private Produccion produccion;

    @Override
    public String toString() {
        return "DetalleProduccion{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", unidad='" + unidad + '\'' +
                ", ingrediente=" + ingrediente +
                ", produccion=" + produccion +
                '}';
    }
}
