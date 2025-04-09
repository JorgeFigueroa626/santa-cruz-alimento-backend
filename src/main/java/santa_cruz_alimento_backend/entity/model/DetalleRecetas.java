package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="detalles_recetas")
public class DetalleRecetas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    @JsonBackReference // 🔥 Indica que esta es la parte "hija" de la relación
    private Receta receta;

    /*@ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;*/

    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false)
    private Base base;


    @Override
    public String toString() {
        return "DetalleRecetas{" +
                "id=" + id +
                ", receta=" + receta +
                ", base=" + base +
                '}';
    }
}
