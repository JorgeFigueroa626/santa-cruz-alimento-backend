package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.response.DetalleProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IProduccionRepository;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProduccionServiceImpl implements IProduccionService {

    @Autowired
    private IProduccionRepository produccionRepository;

    @Autowired
    private IProductRepository productoRepository;

    @Autowired
    private IRecetaRepository recetaRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProduccionServiceImpl.class);

    @Override
    public Produccion calcularProduccion(Long productoId, double produccion_total) throws ExceptionNotFoundException {
        try {

            // Buscar el producto
            Product producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + productoId));

            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + producto.getReceta().getId()));

            if (producto.getReceta().getStatus() == 0)
                throw new ExceptionNotFoundException("Estado de Receta inactiva");

            // Crear nueva lista de ingredientes ajustada
            List<DetalleProduccion> ingredientesAjustados = receta.getDetalleRecetas().stream()
                    .map(ri -> {

                        Ingrediente ingrediente = ingredienteRepository.findById(ri.getIngrediente().getId())
                                .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + ri.getIngrediente().getId()));

                        DetalleProduccion nuevoPI = new DetalleProduccion();
                        nuevoPI.setId(ri.getId());
                        //nuevoPI.setProduccion();
                        nuevoPI.setIngrediente(ri.getIngrediente());
                        nuevoPI.setUnidad(ri.getUnidad());
                        double nuevaCantidad = (produccion_total / producto.getStock()) * ri.getCantidad();

                        // Definir las cantidades
                        double kg = 2.66; // en kilogramos
                        double gramos = 1200; // en gramos

                        // Convertir los kilogramos a gramos
                        double kgEnGramos = ingrediente.getCantidad() * 1000;

                        // Restar los gramos
                        double resultadoEnGramos = kgEnGramos - nuevaCantidad;

                        // Si necesitas el resultado en kilogramos
                        double resultadoEnKg = resultadoEnGramos / 1000;
                        System.out.print(" Operacion " + " = ( " + produccion_total + " / " +  producto.getStock() + " * " + ri.getCantidad()  + " = " + resultadoEnGramos + ri.getUnidad() + " )");


                        /*double kg_a_GR =  1000 * nuevaCantidad;
                        double gr_a_KG = nuevaCantidad / 1000;

                        double lt_a_ML =  1000 * nuevaCantidad;
                        double ml_a_LT = nuevaCantidad / 1000;*/

                        if (resultadoEnKg > ingrediente.getStock()){
                            String mensaje = new StringBuilder()
                                    .append("¡Cantidad del Ingrediente ").append("'" + ingrediente.getName() + "'")
                                    .append(" = ").append(ingrediente.getStock())
                                    .append(" " + ingrediente.getUnidad())
                                    .append("¡ !INSUFICIENTE!\n")
                                    .append(" !Se requiere para la Producción una cantidad mayor a ")
                                    .append(resultadoEnKg)
                                    .append(" ")
                                    .append(ingrediente.getUnidad()+"!")
                                    .toString();

                            throw new ExceptionNotFoundException(mensaje);
                        }else {
                            //ingrediente.getStock(ingrediente.getStock() - nuevaCantidad);
                            //System.out.print(" Inventario del ingrediente " + ingrediente.getName() + " = ( " + ingrediente.getCantidad() + " - " +  nuevaCantidad + " ); ");
                        }

                        nuevoPI.setCantidad(nuevaCantidad);
                        return nuevoPI;
                    })
                    .collect(Collectors.toList());

            // Crear una nueva instancia de Produccion con los ingredientes ajustados y el producto asociado
            Produccion produccion = new Produccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(produccion_total);
            //produccion.setProducido(produccion.getProducido());
            //produccion.setComentario(produccion.getComentario());
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setProducto(producto); // Relacionar con el producto
            produccion.setDetalleProduccions(ingredientesAjustados);

            return produccion;
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Produccion registrarProduccion(Long productoId, double solicitud_producion) throws ExceptionNotFoundException {
        try {

            logger.info("Solicitud de registro: {}", productoId, solicitud_producion);

            // Crear una nueva instancia de Produccion
            Produccion produccion = new Produccion();

            if (productoId == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "producto");

            // Buscar el producto
            Product producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + productoId ));


            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + producto.getReceta().getId()));

            if (producto.getReceta().getStatus() == 0)
                throw new ExceptionNotFoundException("Estado de Receta inactiva");

            // Crear nueva lista de ingredientes ajustada
            List<DetalleProduccion> ingredientesAjustados = receta.getDetalleRecetas().stream()
                    .map(ri -> {

                    Ingrediente ingrediente = ingredienteRepository.findById(ri.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + ri.getIngrediente().getId()));

                        DetalleProduccion nuevoPI = new DetalleProduccion();
                        nuevoPI.setIngrediente(ri.getIngrediente());
                        nuevoPI.setUnidad(ri.getUnidad());
                        double nuevaCantidad = (solicitud_producion / producto.getStock()) * ri.getCantidad();


                        if (nuevaCantidad > ingrediente.getStock()){
                            String mensaje = new StringBuilder()
                                    .append("¡Cantidad del Ingrediente ").append("'" + ingrediente.getName() + "'")
                                    .append(" con ").append(ingrediente.getStock())
                                    .append(" " + ingrediente.getUnidad())
                                    .append("¡ !INSUFICIENTE! \n")
                                    .append("!Se requiere para la Producción una cantidad mayor a ")
                                    .append(nuevaCantidad)
                                    .append(" ")
                                    .append(ingrediente.getUnidad())
                                    .append("!")
                                    .toString();
                            throw new ExceptionNotFoundException(mensaje);
                        }else {
                            ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);
                        }

                        nuevoPI.setCantidad(nuevaCantidad);
                        nuevoPI.setProduccion(produccion); // Establecer la relación correcta


                        return nuevoPI;
                    })
                    .collect(Collectors.toList());

            produccion.setSolicitud_produccion(solicitud_producion);
            produccion.setProducido((int) solicitud_producion);  // Inicialmente en 0
            produccion.setComentario("Nueva producción registrada. Observaciones!");
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setProducto(producto); // Relacionar con el producto

            //producto.setStock((int) (producto.getStock() + solicitud_producion));

            // Asignar ingredientes ajustados a la producción
            produccion.setDetalleProduccions(ingredientesAjustados);

            // Guardar la producción y en cascada los ingredientes
            Produccion save = produccionRepository.save(produccion);

            logger.info("Producion registrado : {}", save);
            return save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Produccion editarProduccionById(Long produccionId, ProduccionRequestDto produccion) {
        try {
            logger.info("Solicitud de modificar: " + produccion);
            // Buscar el producto
            Product producto = productoRepository.findById(produccion.getProductoId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + produccion.getProductoId()));

            // Buscar la producción existente
            Produccion produccionExistente = produccionRepository.findById(produccionId)
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PRODUCTION_WITH_ID + produccionId));

            // Actualizar los campos de la producción existente con los nuevos valores
            //produccionExistente.setSolicitud_produccion(produccion.getSolicitud_produccion());
            produccionExistente.setProducido(produccion.getProducido());  // Actualizar producido si es necesario
            produccionExistente.setComentario(produccion.getComentario()); // Actualizar comentario
            //produccionExistente.setFechaProduccion(produccion.getFecha_produccion()); // Actualizar comentario

            produccionExistente.setProducto(producto); // Relacionar nuevamente con el producto

            //Aumenta el Stock de Producto
            producto.setStock(producto.getStock() + produccion.getProducido());

        /*// Obtener la receta asociada al producto actual
        Receta receta = recetaRepository.findById(produccionExistente.getProducto().getReceta().getId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // Crear nueva lista de ingredientes ajustada según la solicitud de producción
        List<ProduccionIngrediente> ingredientesAjustados = receta.getIngredientes().stream()
                .map(ri -> {
                    ProduccionIngrediente nuevoPI = new ProduccionIngrediente();
                    nuevoPI.setIngrediente(ri.getIngrediente());
                    nuevoPI.setUnidad(ri.getUnidad());
                    // Aquí también podrías actualizar la cantidad de ingredientes si es necesario
                    double nuevaCantidad = (produccionExistente.getSolicitud_produccion() / produccionExistente.getProducto().getStock()) * ri.getCantidad();
                    nuevoPI.setCantidad(nuevaCantidad);
                    nuevoPI.setProduccion(produccionExistente); // Establecer la relación correcta
                    return nuevoPI;
                })
                .collect(Collectors.toList());*/

            // Asignar ingredientes ajustados a la producción existente
            //produccionExistente.setIngredientes(produccion.getIngredientes());

            // Guardar la producción actualizada
            Produccion save = produccionRepository.save(produccionExistente);
            logger.info("Produccion modificado: " + save);
            return  save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public ProduccionResponseDTO getByProduccionId(Long id)  throws ExceptionNotFoundException{
        try {

            Produccion produccion = produccionRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCTION_WITH_ID + id));

            ProduccionResponseDTO dto = new ProduccionResponseDTO();
            dto.setId(produccion.getId());
            dto.setSolicitud_proudcion(produccion.getSolicitud_produccion());
            dto.setProducido(produccion.getProducido());
            dto.setComentario(produccion.getComentario());
            dto.setFecha_produccion(produccion.getFechaProduccion());
            dto.setProductoId(produccion.getProducto().getId());
            dto.setProducto_name(produccion.getProducto().getName());

            // Convertir detalles de recera a DTO
            List<DetalleProduccionResponseDto> detallesDto = produccion.getDetalleProduccions().stream().map(detalle -> {
                DetalleProduccionResponseDto detalleDto = new DetalleProduccionResponseDto();
                detalleDto.setId(detalle.getId());
                detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setUnidad(detalle.getUnidad());
                return detalleDto;
            }).collect(Collectors.toList());

            dto.setDetalleProduccions(detallesDto);

            return dto;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProduccionResponseDTO> findAllProduccions()throws ExceptionNotFoundException {
        try {
            //return produccionRepository.findAll().stream().map(Produccion::produccionDTO).collect(Collectors.toList());
            //return recetaRepository.findAll();
            List<Produccion> produccions = produccionRepository.findAll();

            return produccions.stream().map(produccion -> {
                ProduccionResponseDTO dto = new ProduccionResponseDTO();
                dto.setId(produccion.getId());
                dto.setSolicitud_proudcion(produccion.getSolicitud_produccion());
                dto.setProducido(produccion.getProducido());
                dto.setComentario(produccion.getComentario());
                dto.setFecha_produccion(produccion.getFechaProduccion());
                dto.setProductoId(produccion.getProducto().getId());
                dto.setProducto_name(produccion.getProducto().getName());

                // Convertir detalles de receta a DTO
                List<DetalleProduccionResponseDto> detallesDto = produccion.getDetalleProduccions().stream().map(detalle -> {
                    DetalleProduccionResponseDto detalleDto = new DetalleProduccionResponseDto();
                    detalleDto.setId(detalle.getId());
                    detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setUnidad(detalle.getUnidad());
                    return detalleDto;
                }).collect(Collectors.toList());

                dto.setDetalleProduccions(detallesDto);

                return dto;
            }).collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
