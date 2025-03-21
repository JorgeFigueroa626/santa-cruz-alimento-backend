package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*@Entity
@Data
@Table(name = "subs_products")
public class SubProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer status;

    @OneToMany(mappedBy = "subProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Evita el ciclo al serializar
    private List<DetalleBase> detalleBase = new ArrayList<>();
}*/
