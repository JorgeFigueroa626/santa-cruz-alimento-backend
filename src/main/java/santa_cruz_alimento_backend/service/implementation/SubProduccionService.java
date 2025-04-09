package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.production.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.ProductionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.SubProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.SubProductionRequestDto;
import santa_cruz_alimento_backend.dto.response.category.SubProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.ISubProductionMapper;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.ISubProduccionRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;
import santa_cruz_alimento_backend.service.interfaces.ISubProduccionService;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubProduccionService implements ISubProduccionService {
    
    @Autowired
    private ISubProduccionRepository subProduccionRepository;

    @Autowired
    private ISubProductionMapper subProductionMapper;
    
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubProduccionService.class);

    @Override
    public SubProduccion calcularSubProduction(SubProductionRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + requestDto.getCategoryId()));
            
            if (category.getStatus()==0)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_SUB_PRODUCT_STATUS + category.getName());

            SubProduccion produccion = new SubProduccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(requestDto.getSolicitud_produccion());
            produccion.setProducido(requestDto.getSolicitud_produccion());
            produccion.setComentario("Se producieron " + requestDto.getSolicitud_produccion() + " " + category.getName() + ". Observacion!");
            produccion.setFechaProduccion(requestDto.getFecha_produccion());
            produccion.setStatus(ReplyProduction.EN_PROCESO);

            List<DetalleSubProduccion> detalleSubProduccions = new ArrayList<>();
            
            for (DetalleSubProducto subProducto : category.getDetalleSubProductos()){
                double nuevaCantidad = (requestDto.getSolicitud_produccion() / category.getStock()) * subProducto.getCantidad();

                if (nuevaCantidad > subProducto.getIngrediente().getStock()) {
                    String mensaje = new StringBuilder()
                            .append("¡Cantidad del Ingrediente ").append("'" + subProducto.getIngrediente().getName() + "'")
                            .append(" = ").append(subProducto.getIngrediente().getStock())
                            .append(" " + subProducto.getIngrediente().getUnidad())
                            .append("¡ !INSUFICIENTE!\n")
                            .append(" !Se requiere para la Sub Producción una cantidad mayor a ")
                            .append(nuevaCantidad).append(" " + subProducto.getUnidad()+"!")
                            .toString();
                    logger.info(mensaje);
                    throw new ExceptionNotFoundException(mensaje);
                }else {

                    // Disminuir el stock del ingrediente
                        /*Ingrediente ingrediente = ingredienteRepository.findById(detalleBase.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + detalleBase.getIngrediente().getId()));
                        ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);*/
                    System.out.print(" Inventario del ingrediente " + subProducto.getIngrediente().getName() + " = ( " + subProducto.getIngrediente().getStock() + " - " +  nuevaCantidad + " ); ");
                }
                
                DetalleSubProduccion detalleProduccion = new DetalleSubProduccion();
                detalleProduccion.setIngrediente(subProducto.getIngrediente());
                detalleProduccion.setCantidad(nuevaCantidad);
                detalleProduccion.setUnidad(subProducto.getUnidad());
                
                detalleSubProduccions.add(detalleProduccion);
            }
            produccion.setCategory(category);
            produccion.setDetalleSubProduccion(detalleSubProduccions);
            
            return produccion;
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public SubProduccion solicitarSubProduction(SubProductionRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de sub produccion: {}", requestDto);
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(()->new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + requestDto.getCategoryId()));

            if (category.getStatus() == 0)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_SUB_PRODUCT_STATUS + category.getName());

            SubProduccion subProduccion = new SubProduccion();
            subProduccion.setId(subProduccion.getId());
            subProduccion.setSolicitud_produccion(requestDto.getSolicitud_produccion());
            subProduccion.setProducido(requestDto.getSolicitud_produccion());
            subProduccion.setComentario("Se producieron " + requestDto.getSolicitud_produccion() + " " + category.getName() + ". Observacion!");
            subProduccion.setFechaProduccion(requestDto.getFecha_produccion());
            subProduccion.setStatus(ReplyProduction.EN_PROCESO);
            subProduccion.setCategory(category);

            List<DetalleSubProduccion> detalleSubProduccions = new ArrayList<>();

            for (DetalleSubProducto detalleSubProducto : category.getDetalleSubProductos()){
                double nuevaCantidad = (requestDto.getSolicitud_produccion() / category.getStock()) * detalleSubProducto.getCantidad();

                if (nuevaCantidad > detalleSubProducto.getIngrediente().getStock()) {
                    String mensaje = new StringBuilder()
                            .append("¡Cantidad del Ingrediente ").append("'" + detalleSubProducto.getIngrediente().getName() + "'")
                            .append(" = ").append(detalleSubProducto.getIngrediente().getStock())
                            .append(" " + detalleSubProducto.getIngrediente().getUnidad())
                            .append("¡ !INSUFICIENTE!\n")
                            .append(" !Se requiere para la Sub Producción una cantidad mayor a ")
                            .append(nuevaCantidad).append(" " + detalleSubProducto.getUnidad()+"!")
                            .toString();
                    logger.info(mensaje);
                    throw new ExceptionNotFoundException(mensaje);
                }else {
                    // Disminuir el stock del ingrediente
                        Ingrediente ingrediente = ingredienteRepository.findById(detalleSubProducto.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + detalleSubProducto.getIngrediente().getId()));
                        ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);
                    System.out.print(" Inventario del ingrediente " + detalleSubProducto.getIngrediente().getName() + " = ( " + detalleSubProducto.getIngrediente().getStock() + " - " +  nuevaCantidad + " ); ");
                }

                DetalleSubProduccion produccion = new DetalleSubProduccion();
                produccion.setIngrediente(detalleSubProducto.getIngrediente());
                produccion.setCantidad(nuevaCantidad);
                produccion.setUnidad(detalleSubProducto.getUnidad());

                // Aquí es donde asignamos la relación con la Producción
                produccion.setSubProduccion(subProduccion);

                detalleSubProduccions.add(produccion);
            }
            subProduccion.setDetalleSubProduccion(detalleSubProduccions);

            // Guardar la producción y en cascada los ingredientes
            SubProduccion save = subProduccionRepository.save(subProduccion);
            logger.info("Sub Producion registrado: {}", save);
            return save;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public SubProduccion confirmSubProductionById(Long produccionId, SubProduccionRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Confirma sub produccion. {}", produccionId, requestDto);
            SubProduccion produccion = subProduccionRepository.findById(produccionId)
                    .orElseThrow(()->new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + produccionId));

            Category category = categoryRepository.findById(produccion.getCategory().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + produccion.getCategory().getId()));

            produccion.setProducido(requestDto.getProducido());
            produccion.setComentario(requestDto.getComentario());
            produccion.setFechaProduccion(requestDto.getFecha_produccion());
            produccion.setStatus(requestDto.getStatus());
            produccion.setCategory(category);

            //Aumenta el Stock de Sub Producto
            category.setStock((int) (category.getStock() + requestDto.getProducido()));

            SubProduccion confirm = subProduccionRepository.save(produccion);
            logger.info("Confirmacion de Sub Produccion. {}", confirm);
            return confirm;

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public SubProduccion cancelProductionById(Long produccionId) throws ExceptionNotFoundException {
        try {
            SubProduccion subProduccion = subProduccionRepository.findById(produccionId)
                    .orElseThrow(()->new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCTION_CANCEL + produccionId));
            subProduccion.setStatus(ReplyProduction.CANCELADO);

            // Si necesitas actualizar los ingredientes y cantidad, puedes hacerlo aquí
            subProduccion.getDetalleSubProduccion().forEach(detalle -> {
                Ingrediente ingrediente = detalle.getIngrediente();
                ingrediente.setStock(ingrediente.getStock() + detalle.getCantidad());
                //logger.error("Ingrediente actualizado: {}", ingrediente.getId() + "= " +  ingrediente.getStock());
            });

            SubProduccion cancel = subProduccionRepository.save(subProduccion);
            logger.info("Cancelar sub produccion: {}", produccionId);
            return cancel;
        } catch (Exception e) {
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public SubProduccionResponseDto getBySubProduccionId(Long id) throws ExceptionNotFoundException {
        try {
            SubProduccion subProduccion = subProduccionRepository.findById(id)
                    .orElseThrow(()->new ExceptionNotFoundException(ReplyMessage.MESSAGE_SUB_PRODUCTION_WITH_ID + id));
            SubProduccionResponseDto responseDto = subProductionMapper.toSubProductionResponseDto(subProduccion);
            responseDto.setDetalleSubProduccions(subProductionMapper.toSubProductionDetailResponseDtoList(subProduccion.getDetalleSubProduccion()));
            return responseDto;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<SubProduccionResponseDto> findAllSubProduccions() throws ExceptionNotFoundException {
        try {
            List<SubProduccion> subProduccions = subProduccionRepository.findAll();
            if (subProduccions.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return subProductionMapper.toSubProductionResponseDtoList(subProduccions);

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
