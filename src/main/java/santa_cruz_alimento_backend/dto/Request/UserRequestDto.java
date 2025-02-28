package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

@Data
public class UserRequestDto {

    private Long id;

    private String full_name;

    private String ci;

    private String password;

    private Long rol_id;

    private Long rol_name;
}
