package santa_cruz_alimento_backend.dto.response;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
public class BusinessResponseDto {
    private Long id;

    private String name;

    private ReplyStatus status;
}
