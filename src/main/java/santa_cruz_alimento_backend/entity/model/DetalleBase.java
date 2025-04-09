package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="detalles_bases")
public class DetalleBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;

    private String unidad;

    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false)
    @JsonBackReference // 🔥 Indica que esta es la parte "hija" de la relación
    private Base base;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;


    @Override
    public String toString() {
        return "DetalleBase{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", unidad='" + unidad + '\'' +
                ", base=" + base +
                ", ingrediente=" + ingrediente +
                '}';
    }
}
