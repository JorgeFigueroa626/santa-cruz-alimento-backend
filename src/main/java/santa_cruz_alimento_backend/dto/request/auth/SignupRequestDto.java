package santa_cruz_alimento_backend.dto.request.auth;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String full_name;

    private String ci;

    private String password;

    private Integer status;

    private Long rol_id;
}
