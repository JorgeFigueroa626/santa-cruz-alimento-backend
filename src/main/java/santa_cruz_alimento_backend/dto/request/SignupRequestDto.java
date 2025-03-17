package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
public class SignupRequestDto {

    private String full_name;

    private String ci;

    private String password;

    private Integer status;

    private Long rol_id;
}
