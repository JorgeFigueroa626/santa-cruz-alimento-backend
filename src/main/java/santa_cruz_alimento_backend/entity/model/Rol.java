package santa_cruz_alimento_backend.entity.model;

import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Entity
@Data
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer status;

}
