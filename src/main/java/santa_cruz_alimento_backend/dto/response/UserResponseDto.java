package santa_cruz_alimento_backend.dto.response;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
public class UserResponseDto {

    private Long id;

    private String full_name;

    private String ci;

    private String password;

    private ReplyStatus status;

    private Long rol_id;

    private String rol_name;
}
