package santa_cruz_alimento_backend.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
    private Boolean success;
    private Object data;
    private String message;
}
