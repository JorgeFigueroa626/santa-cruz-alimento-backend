package santa_cruz_alimento_backend.entity.dto;

import lombok.Data;
import santa_cruz_alimento_backend.entity.model.Rol;

@Data
public class UserDto {

    private Long id;

    private String full_name;

    private String ci;

    private String password;

    private Long rol_id;

    private Long rol_name;
}
