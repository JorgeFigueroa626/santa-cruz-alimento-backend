package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

@Data
public class BusinessRequestDto {
    private Long id;

    private String name;

    private ReplyStatus status;
}
