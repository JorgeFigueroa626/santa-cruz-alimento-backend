package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double cantidad;

    private Double stock;

    private String unidad;

    private Integer status;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Evita la serialización infinita
    private List<DetalleCompra> detalleCompras = new ArrayList<>(); // Relación con la entidad DetalleCompra

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Marca este lado como el lado "dueño" de la relación
    private List<DetalleProduccion> detalleProduccions = new ArrayList<>();

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cantidad=" + cantidad +
                ", stock=" + stock +
                ", unidad='" + unidad + '\'' +
                ", status=" + status +
                '}';
    }
}
