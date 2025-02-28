package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalles_compras")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;
    private Double precio;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "compra_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference // 🔥 Indica que esta es la parte "hija" de la relación
    private Compra compra; // Relación con la entidad Compra

    @ManyToOne
    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id", nullable = false)
    private Ingrediente ingrediente; // Relación con la entidad Ingrediente
}
