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

    private String unidad;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Evita la serialización infinita
    private List<DetalleCompra> detalleCompras= new ArrayList<>(); // Relación con la entidad DetalleCompra


    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Evita la serialización infinita
    private List<RecetaIngrediente> recetas = new ArrayList<>(); // ✅ Relación correcta


//    public IngredienteDTO ingredienteDTO() {
//        IngredienteDTO ingredienteDTO = new IngredienteDTO();
//        ingredienteDTO.setId(id);
//        ingredienteDTO.setName(name);
//
//        // Verificar si hay recetas antes de acceder a sus atributos
//        if (!recetas.isEmpty()) {
//            RecetaIngrediente ri = recetas.get(0); // Se toma la primera receta como referencia
//            ingredienteDTO.setUnidad(ri.getUnidad());
//            ingredienteDTO.setCantidad(ri.getCantidad());
//        } else {
//            ingredienteDTO.setUnidad(null);
//            ingredienteDTO.setCantidad(null);
//        }
//
//        return ingredienteDTO;
//    }
}
