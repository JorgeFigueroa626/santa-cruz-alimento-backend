package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "recetas_ingredientes")
public class RecetaIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    @JsonBackReference // 🔥 Indica que esta es la parte "hija" de la relación
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;

    private Double cantidad;
    private String unidad;

}
