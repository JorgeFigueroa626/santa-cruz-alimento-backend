package santa_cruz_alimento_backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CompraResponseDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fecha_compra;

    private Double total;

    private List<DetalleCompraResponseDto> detalleCompras;
}
