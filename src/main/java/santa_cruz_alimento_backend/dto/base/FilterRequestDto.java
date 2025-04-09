package santa_cruz_alimento_backend.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class FilterRequestDto {
    private String default_page_number;
    private String default_page_size;
    private String search_text;
    private String asc;
    private String desc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private String start_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private String end_date;
}