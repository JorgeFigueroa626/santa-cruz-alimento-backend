package santa_cruz_alimento_backend.dto.request;

import lombok.Data;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Data
public class BiscochueloRequesDto {

    private String name;

    private ReplyStatus status;

    private List<DetalleBiscochueloRequestDto> ingredientes;
}
