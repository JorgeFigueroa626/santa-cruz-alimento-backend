package santa_cruz_alimento_backend.entity.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
