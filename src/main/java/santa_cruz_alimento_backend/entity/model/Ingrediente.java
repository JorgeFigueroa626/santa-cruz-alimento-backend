package santa_cruz_alimento_backend.entity.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer unidad;

    private Integer stock;

//    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL)
//    private Set<Receta> receta;
}
