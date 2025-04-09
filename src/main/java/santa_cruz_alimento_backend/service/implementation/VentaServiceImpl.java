package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.sale.DetalleVentasRequestDto;
import santa_cruz_alimento_backend.dto.request.sale.VentaRequesDto;
import santa_cruz_alimento_backend.dto.response.sale.VentaResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.ISaleMapper;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.repository.IVentaRepository;
import santa_cruz_alimento_backend.service.interfaces.IVentaService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private ISaleMapper saleMapper;

    private static final Logger logger = LoggerFactory.getLogger(VentaServiceImpl.class);

    @Transactional
    @Override
    public Venta registrarVenta(VentaRequesDto ventaDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", ventaDTO);

            Venta venta = new Venta();

            if (ventaDTO.getUsuarioId() == null) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "usuario");
            }
            Usuario usuario = userRepository.findById(ventaDTO.getUsuarioId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_USER_WITH_ID + ventaDTO.getUsuarioId()));

            venta.setFechaVenta(new Timestamp(System.currentTimeMillis()));
            venta.setUsuario(usuario);

            List<DetalleVenta> detalles = new ArrayList<>();
            double totalVenta = 0.0;

            for (DetalleVentasRequestDto detalleDTO : ventaDTO.getDetallesVentas()) {
                Product producto = productRepository.findById(detalleDTO.getProductoId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + detalleDTO.getProductoId()));

                if (detalleDTO.getCantidad() > producto.getStock()) {
                    throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_PURCHASE_INSUFFICIENT_STOCK_SALE + producto.getName());
                }

                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(detalleDTO.getCantidad());
                detalleVenta.setPrecioUnitario(producto.getPrice());
                detalleVenta.setSubtotal((double) (detalleDTO.getCantidad() * producto.getPrice()));
                detalleVenta.setVenta(venta);

                detalles.add(detalleVenta);
                totalVenta += detalleVenta.getSubtotal();

                // Reducir stock del producto
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            }

            venta.setDetallesVentas(detalles);
            venta.setTotal(totalVenta);

            Venta save = ventaRepository.save(venta);

            logger.info("Venta registrado: {}", save);
            return save;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public VentaResponseDto getByVentaId(Long id) throws ExceptionNotFoundException {
        try {
            Venta venta = ventaRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_SALE_WITH_ID + id));

            // Convertimos la venta a DTO
            VentaResponseDto dto = saleMapper.toSaleResponseDto(venta);

            // Convertimos los detalles de venta a DTO
            dto.setDetallesVentas(saleMapper.toSaleDetailResponseListDto(venta.getDetallesVentas()));

            return dto;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<VentaResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            List<Venta> ventas = ventaRepository.findAll();
            if (ventas.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return saleMapper.toSaleResponseDtoList(ventas);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public List<VentaResponseDto> findAllVentasByUsuarioId(Long userId) throws ExceptionNotFoundException {

        try {
            List<Venta> ventas = ventaRepository.findByUsuarioId(userId);
            if (ventas.isEmpty())
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);

            return saleMapper.toSaleResponseDtoList(ventas);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    /*private VentaResponseDto convertirVentaADto(Venta venta) {
        VentaResponseDto dto = new VentaResponseDto();
        dto.setId(venta.getId());
        dto.setFecha_venta(venta.getFechaVenta());
        dto.setUsuarioId(venta.getUsuario().getId());
        dto.setUsuario_nombre(venta.getUsuario().getFullName());
        dto.setTotal(venta.getTotal());
        // Convertir detalles de venta a DTO usando el método privado
        dto.setDetallesVentas(convertirDetallesVentaADto(venta.getDetallesVentas()));

        return dto;
    }

    private List<DetalleVentasResponseDto> convertirDetallesVentaADto(List<DetalleVenta> detallesVentas) {
        return detallesVentas.stream().map(detalle -> {
            DetalleVentasResponseDto detalleDto = new DetalleVentasResponseDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setNombre_producto(detalle.getProducto().getName());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setPrecio_unitario(detalle.getPrecioUnitario());
            detalleDto.setSub_total(detalle.getSubtotal());
            return detalleDto;
        }).collect(Collectors.toList());
    }*/


}
