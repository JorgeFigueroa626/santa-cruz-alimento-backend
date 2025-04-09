package santa_cruz_alimento_backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import santa_cruz_alimento_backend.dto.response.sale.DetalleVentasResponseDto;
import santa_cruz_alimento_backend.dto.response.sale.VentaResponseDto;
import santa_cruz_alimento_backend.entity.model.DetalleVenta;
import santa_cruz_alimento_backend.entity.model.Venta;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISaleMapper {

    @Mapping(target = "id")
    @Mapping(source = "fechaVenta", target = "fecha_venta", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.fullName", target = "usuario_nombre")
    @Mapping(target = "total")
    VentaResponseDto toSaleResponseDto(Venta venta);
    List<VentaResponseDto> toSaleResponseDtoList(List<Venta> ventas);

    @Mapping(target = "id")
    @Mapping(source = "producto.name", target = "nombre_producto")
    @Mapping(source = "producto.image", target = "image_producto")
    @Mapping(target = "cantidad")
    @Mapping(source = "precioUnitario", target = "precio_unitario")
    @Mapping(source = "subtotal", target = "sub_total")
    DetalleVentasResponseDto toSaleDetailResponseDto(DetalleVenta detalleVenta);
    List<DetalleVentasResponseDto> toSaleDetailResponseListDto(List<DetalleVenta> detalleVentas);
}
