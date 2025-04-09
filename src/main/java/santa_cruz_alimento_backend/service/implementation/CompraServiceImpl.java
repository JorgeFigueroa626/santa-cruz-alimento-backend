package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.purchase.CompraRequestDto;
import santa_cruz_alimento_backend.dto.response.purchase.CompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.ICompraMapper;
import santa_cruz_alimento_backend.repository.ICompraRepository;
import santa_cruz_alimento_backend.repository.IDetalleComprasRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.ICompraService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements ICompraService {

    @Autowired
    private ICompraRepository compraRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IDetalleComprasRepository detalleCompraRepository;

    @Autowired
    private ICompraMapper compraMapper;

    private static final Logger logger = LoggerFactory.getLogger(CompraServiceImpl.class);

    @Transactional
    @Override
    public Compra createCompra(CompraRequestDto compraRequestDto) throws ExceptionNotFoundException {
        try {

            logger.info("Solicitud de registro: {}", compraRequestDto);

            /*Compra compra = new Compra();
            compra.setFechaCompra(compraRequestDto.getFecha_compra());*/
            Compra compra = compraMapper.toPurchaseRequestDto(compraRequestDto);
            compra.setFechaCompra(compraRequestDto.getFecha_compra());

            List<DetalleCompra> detalleCompras = compraRequestDto.getDetallesCompras().stream().map(iDto ->{
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

                ingrediente.setStock(ingrediente.getStock() + iDto.getCantidad());

                DetalleCompra detalleCompra = new DetalleCompra();
                detalleCompra.setIngrediente(ingrediente);
                detalleCompra.setCantidad(iDto.getCantidad());
                detalleCompra.setPrecio(iDto.getPrecio());
                detalleCompra.setTotal(iDto.getCantidad() * iDto.getPrecio());

                detalleCompra.setCompra(compra);

                return detalleCompra;
            }).collect(Collectors.toList());

            compra.setDetalleCompras(detalleCompras);

            // Calcular el total de la compra sumando los totales de cada detalle de compra
            double totalCompra = detalleCompras.stream().mapToDouble(DetalleCompra::getTotal).sum();
            compra.setTotal(totalCompra);

            Compra save = compraRepository.save(compra);

            logger.info("Compra registrado : {}", save);
            return save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public List<CompraResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            List<Compra> compras = compraRepository.findAll();
            if (compras.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return compraMapper.toCompraResponseDtoList(compras);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public CompraResponseDto getByCompraId(Long id) throws ExceptionNotFoundException{
        try {
            Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PURCHASE_WITH_ID + id));
            CompraResponseDto dto = compraMapper.toCompraResponseDto(compra);
            dto.setDetalleCompras(compraMapper.toDetalleCompraResponseDtoList(compra.getDetalleCompras()));
            return dto;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    // Obtener todas las compras de un ingrediente específico
    public List<Compra> obtenerComprasPorIngrediente(Long ingredienteId)throws ExceptionNotFoundException {
        try {
            // Obtener todos los detalles de compra relacionados con el ingrediente
            List<DetalleCompra> detallesCompra = detalleCompraRepository.findByIngredienteId(ingredienteId);
            if (detallesCompra.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }

            // Extraer las compras de los detalles obtenidos
            List<Compra> compras = detallesCompra.stream()
                    .map(DetalleCompra::getCompra) // Obtener la compra asociada a cada detalle de compra
                    .distinct() // Eliminar duplicados
                    .collect(Collectors.toList());

            return compras;

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean updateById(Long id, CompraRequestDto requestDto) throws ExceptionNotFoundException {
        Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PURCHASE_WITH_ID + id));
        return false;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PURCHASE_WITH_ID + id));
            compraRepository.deleteById(compra.getId());
            logger.info("Compra eliminado : {}", compra);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    /*private CompraResponseDto convertPurchaseDto(Compra compra ) {
        CompraResponseDto dto = new CompraResponseDto();
        dto.setId(compra.getId());
        dto.setFecha_compra(compra.getFechaCompra());
        dto.setTotal(compra.getTotal());
        //dto.setUsuarioNombre(venta.ventaResponseDto().getUsuarioNombre()); // Obtener nombre del usuario
        dto.setDetallesCompras(convertPurchaseDetailDto(compra.getDetalleCompras()));
        return dto;
    }

    private List<DetalleCompraResponseDto> convertPurchaseDetailDto(List<DetalleCompra> detalleCompra){
        return detalleCompra.stream().map(detalle -> {
            DetalleCompraResponseDto detalleDto = new DetalleCompraResponseDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setUnidad(detalle.getIngrediente().getUnidad());
            detalleDto.setPricio_unitario(detalle.getPrecio());
            detalleDto.setSub_total(detalle.getTotal());
            return detalleDto;
        }).collect(Collectors.toList());
    }*/
}
