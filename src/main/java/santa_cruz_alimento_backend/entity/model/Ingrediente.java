package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    private String unidad;
//
//    private Double stock;

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
