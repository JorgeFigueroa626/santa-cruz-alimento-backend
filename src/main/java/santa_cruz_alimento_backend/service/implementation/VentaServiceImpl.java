package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.DetalleVentasRequestDto;
import santa_cruz_alimento_backend.dto.request.VentaRequesDto;
import santa_cruz_alimento_backend.dto.response.DetalleVentasResponseDto;
import santa_cruz_alimento_backend.dto.response.VentaResponseDto;
import santa_cruz_alimento_backend.dto.response.VentaUserResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.repository.IVentaRepository;
import santa_cruz_alimento_backend.service.interfaces.IVentaService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IVentaRepository ventaRepository;

    private static final Logger logger = LoggerFactory.getLogger(VentaServiceImpl.class);

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

                if (producto.getStock() < detalleDTO.getCantidad()) {
                    throw new ExceptionNotFoundException("Stock insuficiente para la Venta del producto: " + producto.getName());
                }

                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setProducto(producto);
                detalleVenta.setCantidad(detalleDTO.getCantidad());
                detalleVenta.setPrecioUnitario(Double.valueOf(producto.getPrice()));
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

            VentaResponseDto dto = new VentaResponseDto();
            dto.setId(venta.getId());
            dto.setFechaVenta(venta.getFechaVenta());
            dto.setTotal(venta.getTotal());
            dto.setUsuario_nombre(venta.getUsuario().getFullName()); // Obtener nombre del usuario

            // Convertir detalles de venta a DTO
            List<DetalleVentasResponseDto> detallesDto = venta.getDetallesVentas().stream().map(detalle -> {
                DetalleVentasResponseDto detalleDto = new DetalleVentasResponseDto();
                detalleDto.setId(detalle.getId());
                detalleDto.setNombre_producto(detalle.getProducto().getName());
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setPrecio_unitario(detalle.getPrecioUnitario());
                detalleDto.setSub_total(detalle.getSubtotal());
                return detalleDto;
            }).collect(Collectors.toList());

            dto.setDetallesVentas(detallesDto);

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

            return ventas.stream().map(venta -> {
                VentaResponseDto dto = new VentaResponseDto();
                dto.setId(venta.getId());
                dto.setFechaVenta(venta.getFechaVenta());
                dto.setTotal(venta.getTotal());
                dto.setUsuario_nombre(venta.getUsuario().getFullName()); // Obtener nombre del usuario

                // Convertir detalles de venta a DTO
                List<DetalleVentasResponseDto> detallesDto = venta.getDetallesVentas().stream().map(detalle -> {
                    DetalleVentasResponseDto detalleDto = new DetalleVentasResponseDto();
                    detalleDto.setId(detalle.getId());
                    detalleDto.setNombre_producto(detalle.getProducto().getName());
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setPrecio_unitario(detalle.getPrecioUnitario());
                    detalleDto.setSub_total(detalle.getSubtotal());
                    return detalleDto;
                }).collect(Collectors.toList());

                dto.setDetallesVentas(detallesDto);

                return dto;
            }).collect(Collectors.toList());

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public List<VentaUserResponseDto> findAllVentasByUsuarioId(Long userId) throws ExceptionNotFoundException {

        try {
            List<Venta> ventas = ventaRepository.findByUsuarioId(userId);

            return ventas.stream().map(venta -> {
                VentaUserResponseDto dto = new VentaUserResponseDto();
                dto.setId(venta.getId());
                dto.setFecha_venta(venta.getFechaVenta());
                dto.setUsuarioId(venta.getUsuario().getId());
                dto.setUsuario_nombre(venta.getUsuario().getFullName());
                dto.setTotal(venta.getTotal());

                // Convertir detalles de venta a DTO
                List<DetalleVentasResponseDto> detallesDto = venta.getDetallesVentas().stream().map(detalle -> {
                    DetalleVentasResponseDto detalleDto = new DetalleVentasResponseDto();
                    detalleDto.setId(detalle.getId());
                    detalleDto.setNombre_producto(detalle.getProducto().getName());
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setPrecio_unitario(detalle.getPrecioUnitario());
                    detalleDto.setSub_total(detalle.getSubtotal());
                    return detalleDto;
                }).collect(Collectors.toList());

                dto.setDetallesVentas(detallesDto);

                return dto;
            }).collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

}
