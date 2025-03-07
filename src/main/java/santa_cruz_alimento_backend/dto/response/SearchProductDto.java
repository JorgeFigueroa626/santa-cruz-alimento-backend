package santa_cruz_alimento_backend.dto.response;

import lombok.Data;

@Data
public class SearchProductDto {

    private String name;

    private String category;

    private String business;
}
