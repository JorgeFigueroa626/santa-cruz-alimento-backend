package santa_cruz_alimento_backend.util.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {

    private Boolean success;
    private Object data;
    private String message;

}
