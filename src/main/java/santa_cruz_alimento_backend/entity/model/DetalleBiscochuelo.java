package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

/*@Entity
@Data
@Table(name ="detalles_biscochuelos")*/
public class DetalleBiscochuelo {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;

    private String unidad;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference // 🔥 Indica que esta es la parte "hija" de la relación
    private Category category;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;*/
}
