package santa_cruz_alimento_backend.dto.Request;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String full_name;

    private String ci;

    private String password;

    private Long rol_id;
}
