package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "produccions_ingredientes")
public class ProduccionIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produccion_id", referencedColumnName = "id", nullable = false) // Relación con Producción
    @JsonBackReference  // Evita la serialización infinita
    private Produccion produccion;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;

    private Double cantidad;

    private String unidad;
}
