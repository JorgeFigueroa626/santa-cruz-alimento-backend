package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.request.purchase.CompraRequestDto;
import santa_cruz_alimento_backend.dto.request.purchase.DetalleCompraRequestDto;
import santa_cruz_alimento_backend.dto.response.purchase.CompraResponseDto;
import santa_cruz_alimento_backend.dto.response.purchase.DetalleCompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICompraMapper {

    @Mapping(target = "id")
    @Mapping(source = "fechaCompra", target = "fecha_compra", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "total")
    CompraResponseDto toCompraResponseDto(Compra compra);
    List<CompraResponseDto> toCompraResponseDtoList(List<Compra> compras);

    @Mapping(target = "id")
    @Mapping(source = "ingrediente.name", target = "nombre_ingrediente")
    @Mapping(target = "cantidad")
    @Mapping(source = "ingrediente.unidad", target = "unidad")
    @Mapping(source = "precio", target = "pricio_unitario")
    @Mapping(source = "total", target = "sub_total")
    DetalleCompraResponseDto toDetalleCompraResponseDto(DetalleCompra detalleCompra);
    List<DetalleCompraResponseDto> toDetalleCompraResponseDtoList(List<DetalleCompra> detallesCompra);


    @Mapping(target = "id", ignore = true)
    Compra toPurchaseRequestDto(CompraRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingrediente", ignore = true) // Se asignará manualmente en el servicio
    @Mapping(target = "compra", ignore = true) // Se asignará manualmente en el servicio
    DetalleCompra toPurchaseDetailRequest(DetalleCompraRequestDto detalleCompraRequestDto);
    DetalleCompraRequestDto toPurchaseDetailRequestDto(DetalleCompra detalleCompra);
}
