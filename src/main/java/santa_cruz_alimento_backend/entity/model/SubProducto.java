package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
@Entity
@Data
@Table(name = "sub_productos")
public class SubProducto {

    @Id
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
    private Ingrediente ingrediente;


    @Override
    public String toString() {
        return "SubProducto{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", unidad='" + unidad + '\'' +
                ", ingrediente=" + ingrediente +
                ", category=" + category +
                '}';
    }
}*/
