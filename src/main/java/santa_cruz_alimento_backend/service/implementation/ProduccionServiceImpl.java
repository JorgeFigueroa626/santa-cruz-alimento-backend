package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.production.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.ProductionRequestDto;
import santa_cruz_alimento_backend.dto.response.product.ProductoResponseDTO;
import santa_cruz_alimento_backend.dto.response.production.DetalleProduccionResponseDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.dto.response.production.ProductionRecipeBaseResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IBaseMapper;
import santa_cruz_alimento_backend.mapper.IProductionMapper;
import santa_cruz_alimento_backend.repository.*;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private IBaseRepository baseRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IProductionMapper productionMapper;

    @Autowired
    private IBaseMapper baseMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProduccionServiceImpl.class);

    @Transactional
    @Override
    public Produccion calcularProduccion(ProductionRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            // Buscar el producto
            Product producto = productoRepository.findById(requestDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + requestDto.getProductoId()));

            if (producto.getStatus() == 0) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_PRODUCT_STATUS + producto.getName());
            }
            
            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + producto.getReceta().getId()));

            if (receta.getStatus() == 0)
                throw new RuntimeException(ReplyMessage.MESSAGE_NOT_STATUS + receta.getName());


            // Crear nueva lista de bases ajustadas con sus ingredientes
            List<DetalleProduccion> detalleProduccionList = new ArrayList<>();

            double subProducto = (requestDto.getSolicitud_produccion() / producto.getStock()) * producto.getCategory().getUnidad();
            /*if (subProducto > producto.getCategory().getStock()){
                throw new ExceptionNotFoundException("¡Stock de sub producto '" +
                        producto.getCategory().getName()+ "'" + "= "+ producto.getCategory().getStock() + ". INSUFICIENTE!. "+
                        "!Se requiere para la Producción una cantidad mayor o igual " + subProducto + " " + producto.getName());
            }else {
            }*/
            // Disminuir el stock del categoria
            Category category = categoryRepository.findById(producto.getCategory().getId())
                    .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + producto.getCategory().getId()));
            //category.setStock((int) (category.getStock() - subProducto));
            System.out.print("Stock de sub producto " + category.getStock() + " " + subProducto);
            System.out.print(" Inventario del categoria " + producto.getCategory().getName() + " = ( " + producto.getCategory().getStock() + " - " +  subProducto + " ); ");

            for (DetalleRecetas detalleRecetas : receta.getDetalleRecetas()) {
                for (DetalleBase detalleBase : detalleRecetas.getBase().getDetalleBases()) {
                    double nuevaCantidad = (requestDto.getSolicitud_produccion() / producto.getStock()) * detalleBase.getCantidad();

                    System.out.print(" Operacion " + " = ( " + requestDto.getSolicitud_produccion() + " / " +  producto.getStock() + " * " + detalleBase.getIngrediente().getStock()  + " = " + nuevaCantidad + detalleBase.getUnidad() + " )");

                    if (nuevaCantidad > detalleBase.getIngrediente().getStock()){
                        String mensaje = new StringBuilder()
                                .append("¡Cantidad del Ingrediente ").append("'" + detalleBase.getIngrediente().getName() + "'")
                                .append(" = ").append(detalleBase.getIngrediente().getStock())
                                .append(" " + detalleBase.getIngrediente().getUnidad())
                                .append("¡ !INSUFICIENTE!\n")
                                .append(" !Se requiere para la Producción una cantidad mayor a ")
                                .append(nuevaCantidad)
                                .append(" ")
                                .append(detalleBase.getUnidad()+"!")
                                .toString();
                        throw new ExceptionNotFoundException(mensaje);
                    }else {

                        // Disminuir el stock del ingrediente
                        /*Ingrediente ingrediente = ingredienteRepository.findById(detalleBase.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + detalleBase.getIngrediente().getId()));
                        ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);*/
                        System.out.print(" Inventario del ingrediente " + detalleBase.getIngrediente().getName() + " = ( " + detalleBase.getIngrediente().getStock() + " - " +  nuevaCantidad + " ); ");
                    }

                    DetalleProduccion detalleProduccion = new DetalleProduccion();
                    detalleProduccion.setIngrediente(detalleBase.getIngrediente());
                    //detalleProduccion.setNombreIngrediente(ingrediente.getNombreIngrediente());
                    detalleProduccion.setNombreBase(detalleBase.getBase().getName());
                    detalleProduccion.setCantidad(nuevaCantidad);
                    detalleProduccion.setUnidad(detalleBase.getUnidad());
                    detalleProduccionList.add(detalleProduccion);
                }
            }


            // Crear una nueva instancia de Produccion con los ingredientes ajustados
            Produccion produccion = new Produccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(requestDto.getSolicitud_produccion());
            produccion.setProducido(requestDto.getSolicitud_produccion());
            produccion.setComentario("Se producieron " + requestDto.getSolicitud_produccion() + " " + producto.getName() + ". Observacion!");
            produccion.setFechaProduccion(requestDto.getFecha_produccion());
            produccion.setNombre_sub_producto(producto.getCategory().getName());
            produccion.setCantidad_sub_producto((int) subProducto);
            //System.out.print("Cantida Sub Producto: {} " + subProducto);
            produccion.setStatus(ReplyProduction.EN_PROCESO);
            produccion.setProducto(producto);
            produccion.setDetalleProduccions(detalleProduccionList);

            return produccion;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

        /*try {

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
                        nuevoPI.setCantidad(nuevaCantidad);
                        return nuevoPI;
                    })
                    .collect(Collectors.toList())

            // Crear una nueva instancia de Produccion con los ingredientes ajustados y el producto asociado
            Produccion produccion = new Produccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(produccion_total);
            //produccion.setProducido(produccion.getProducido());
            //produccion.setComentario(produccion.getComentario());
            produccion.setFechaProduccion(produccion.getFecha_produccion);
            produccion.setProducto(producto); // Relacionar con el producto
            produccion.setDetalleProduccions(ingredientesAjustados);

            return produccion;
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }*/

    }


    @Transactional
    @Override
    public Produccion solicitarProduccion(ProductionRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud del " + requestDto.getProductoId() + " para producir "  + requestDto.getSolicitud_produccion());

            //Produccion produccions = produccionRepository.findById(requestDto.getProductionId().getId()).get();

            // Buscar el producto
            Product producto = productoRepository.findById(requestDto.getProductoId())
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + requestDto.getProductoId()));

            if (producto.getStatus() == 0) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_PRODUCT_STATUS + producto.getName());
            }

            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + producto.getReceta().getId()));

            if (receta.getStatus() == 0)
                throw new RuntimeException(ReplyMessage.MESSAGE_NOT_RECIPE_STATUS + receta.getName());

            // Crear una nueva instancia de Produccion con los ingredientes ajustados
            Produccion produccion = new Produccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(requestDto.getSolicitud_produccion());
            produccion.setProducido(requestDto.getSolicitud_produccion());
            produccion.setComentario("Se producieron " + requestDto.getSolicitud_produccion() + " " + producto.getName() + ". Observacion!");
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setStatus(ReplyProduction.EN_PROCESO);

            Category category = categoryRepository.findById(producto.getCategory().getId())
                    .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + producto.getCategory().getId()));
            double subProducto = (requestDto.getSolicitud_produccion() / producto.getStock()) * producto.getCategory().getUnidad();
            category.setStock((int) (category.getStock() - subProducto));

            // Crear nueva lista de bases ajustadas con sus ingredientes
            List<DetalleProduccion> detalleProduccionList = new ArrayList<>();



            for (DetalleRecetas detalleRecetas : receta.getDetalleRecetas()) {
                for (DetalleBase detalleBase : detalleRecetas.getBase().getDetalleBases()) {
                    double nuevaCantidad = (requestDto.getSolicitud_produccion() / producto.getStock()) * detalleBase.getCantidad();

                    System.out.print(" Operacion " + " = ( " + requestDto.getSolicitud_produccion() + " / " +  producto.getStock() + " * " + detalleBase.getIngrediente().getStock()  + " = " + nuevaCantidad + detalleBase.getUnidad() + " )");

                    if (nuevaCantidad > detalleBase.getIngrediente().getStock()){
                        String mensaje = new StringBuilder()
                                .append("¡Cantidad del Ingrediente ").append("'" + detalleBase.getIngrediente().getName() + "'")
                                .append(" = ").append(detalleBase.getIngrediente().getStock())
                                .append(" " + detalleBase.getIngrediente().getUnidad())
                                .append("¡ !INSUFICIENTE!\n")
                                .append(" !Se requiere para la Producción una cantidad mayor a ")
                                .append(nuevaCantidad)
                                .append(" ")
                                .append(detalleBase.getUnidad()+"!")
                                .toString();
                        throw new ExceptionNotFoundException(mensaje);
                    }else {

                        // Disminuir el stock del ingrediente
                        Ingrediente ingrediente = ingredienteRepository.findById(detalleBase.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + detalleBase.getIngrediente().getId()));
                        ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);
                        System.out.print(" Inventario del ingrediente " + detalleBase.getIngrediente().getName() + " = ( " + detalleBase.getIngrediente().getStock() + " - " +  nuevaCantidad + " ); ");
                    }

                    // Crear y asociar DetalleProduccion
                    DetalleProduccion detalleProduccion = new DetalleProduccion();
                    detalleProduccion.setIngrediente(detalleBase.getIngrediente());
                    detalleProduccion.setNombreBase(detalleBase.getBase().getName());
                    detalleProduccion.setCantidad(nuevaCantidad);
                    detalleProduccion.setUnidad(detalleBase.getUnidad());

                    // Aquí es donde asignamos la relación con la Producción
                    detalleProduccion.setProduccion(produccion);  // Asignación del objeto Produccion

                    detalleProduccionList.add(detalleProduccion);
                }
            }
            produccion.setProducto(producto);
            produccion.setDetalleProduccions(detalleProduccionList);

            //producto.setStock((int) (producto.getStock() + produccion.getProducido()));

            // Guardar la producción y en cascada los ingredientes
            Produccion save = produccionRepository.save(produccion);

            logger.info("Producción registrada : {}", save);
            return save;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
        /*try {

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
        }*/
    }

    /*@Service
    @RequiredArgsConstructor
    @Slf4j
    public class ProduccionService {

        private final ProductoRepository productoRepository;
        private final RecetaRepository recetaRepository;
        private final IngredienteRepository ingredienteRepository;
        private final ProduccionRepository produccionRepository;

        public Produccion registrarProduccion(Long productoId, double solicitudProduccion) {
            log.info("Solicitud de registro: Producto ID={}, Solicitud Producción={}", productoId, solicitudProduccion);

            if (productoId == null || solicitudProduccion <= 0)
                throw new ExceptionNotFoundException("El ID del producto y la solicitud de producción son obligatorios y deben ser mayores a 0.");

            // 1️⃣ Buscar Producto
            Producto producto = buscarProducto(productoId);

            // 2️⃣ Buscar Receta Asociada
            Receta receta = buscarReceta(producto);

            // 3️⃣ Ajustar Ingredientes
            List<DetalleProduccion> ingredientesAjustados = ajustarIngredientes(receta, solicitudProduccion, producto);

            // 4️⃣ Crear y Guardar Producción
            Produccion produccion = guardarProduccion(producto, solicitudProduccion, ingredientesAjustados);

            log.info("Producción registrada: {}", produccion);
            return produccion;
        }

        // 📌 Buscar Producto
        private Producto buscarProducto(Long productoId) {
            return productoRepository.findById(productoId)
                    .orElseThrow(() -> new ExceptionNotFoundException("No se encontró el Producto con ID " + productoId));
        }

        // 📌 Buscar Receta Asociada
        private Receta buscarReceta(Producto producto) {
            return recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException("No se encontró la Receta con ID " + producto.getReceta().getId()));
        }

        // 📌 Ajustar Ingredientes según la Producción Solicitada
        private List<DetalleProduccion> ajustarIngredientes(Receta receta, double solicitudProduccion, Producto producto) {
            if (receta.getStatus() == 0)
                throw new ExceptionNotFoundException("Estado de Receta inactiva");

            return receta.getDetalleRecetas().stream()
                    .map(ri -> calcularIngredienteAjustado(ri, solicitudProduccion, producto))
                    .collect(Collectors.toList());
        }

        private DetalleProduccion calcularIngredienteAjustado(DetalleReceta ri, double solicitudProduccion, Producto producto) {
            Ingrediente ingrediente = ingredienteRepository.findById(ri.getIngrediente().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException("No se encontró el Ingrediente con ID " + ri.getIngrediente().getId()));

            // 🛠 Cálculo de Cantidad Ajustada
            double nuevaCantidad = (solicitudProduccion / producto.getStock()) * ri.getCantidad();

            if (nuevaCantidad > ingrediente.getStock()) {
                throw new ExceptionNotFoundException(String.format(
                        "¡Cantidad insuficiente de %s! Stock disponible: %.2f %s, se requiere: %.2f %s.",
                        ingrediente.getNombre(), ingrediente.getStock(), ingrediente.getUnidad(), nuevaCantidad, ingrediente.getUnidad()
                ));
            }

            // Reducir stock del ingrediente
            ingrediente.setStock(ingrediente.getStock() - nuevaCantidad);

            // Crear el Detalle de Producción
            DetalleProduccion detalleProduccion = new DetalleProduccion();
            detalleProduccion.setIngrediente(ingrediente);
            detalleProduccion.setUnidad(ri.getUnidad());
            detalleProduccion.setCantidad(nuevaCantidad);

            return detalleProduccion;
        }

        // 📌 Crear y Guardar Producción
        private Produccion guardarProduccion(Producto producto, double solicitudProduccion, List<DetalleProduccion> ingredientesAjustados) {
            Produccion produccion = new Produccion();
            produccion.setProducto(producto);
            produccion.setSolicitud_produccion(solicitudProduccion);
            produccion.setProducido((int) solicitudProduccion);
            produccion.setComentario("Nueva producción registrada.");
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setDetalleProduccions(ingredientesAjustados);

            return produccionRepository.save(produccion);
        }
    }
*/

    @Transactional
    @Override
    public Produccion confirmProductionById(Long produccionId, ProduccionRequestDto produccion) {
        try {
            logger.info("Confirmacion de produccion: " + produccion);
            // Buscar la producción existente
            Produccion produccionExistente = produccionRepository.findById(produccionId)
                    .orElseThrow(() -> new RuntimeException(ReplyMessage.MESSAGE_PRODUCTION_WITH_ID + produccionId));

            // Buscar el producto
            Product producto = productoRepository.findById(produccionExistente.getProducto().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + produccion.getProductoId()));


            // Actualizar los campos de la producción existente con los nuevos valores
            //produccionExistente.setSolicitud_produccion(produccion.getSolicitud_produccion());
            produccionExistente.setProducido(produccion.getProducido());  // Actualizar producido si es necesario
            produccionExistente.setComentario(produccion.getComentario()); // Actualizar comentario
            produccionExistente.setFechaProduccion(produccion.getFecha_produccion()); // Actualizar comentario
            produccionExistente.setStatus(produccion.getStatus());
            produccionExistente.setNombre_sub_producto(produccionExistente.getNombre_sub_producto());
            produccionExistente.setCantidad_sub_producto(produccionExistente.getCantidad_sub_producto());
            produccionExistente.setProducto(producto); // Relacionar nuevamente con el producto

            //Aumenta el Stock de Producto
            producto.setStock((int) (producto.getStock() + produccion.getProducido()));

            // Guardar la producción actualizada
            Produccion save = produccionRepository.save(produccionExistente);
            logger.info("Produccion confirmado: " + save);
            return  save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Produccion cancelProductionById(Long produccionId) throws ExceptionNotFoundException {
        try {
            logger.info("Cancelar la produccion: {}", produccionId);
            Produccion produccion = produccionRepository.findById(produccionId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCTION_WITH_ID + produccionId));

            // Actualizar el estado de la producción
            produccion.setStatus(ReplyProduction.CANCELADO);

            // Si necesitas actualizar los ingredientes y cantidad, puedes hacerlo aquí
            produccion.getDetalleProduccions().forEach(detalle -> {
                Ingrediente ingrediente = detalle.getIngrediente();
                ingrediente.setStock(ingrediente.getStock() + detalle.getCantidad());
                logger.error("Ingrediente actualizado: {}", ingrediente.getId() + "= " +  ingrediente.getStock());
            });

            // Actualiza la producción en la base de datos, sin modificar la lista de detalles
            Produccion cancel = produccionRepository.save(produccion);
            logger.info("Producción cancelada: {}", cancel);
            return cancel;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }



    @Override
    public ProduccionResponseDTO getByProduccionId(Long id)  throws ExceptionNotFoundException{
        try {

            Produccion produccion = produccionRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCTION_WITH_ID + id));

            ProduccionResponseDTO dto = productionMapper.toProductionResponseDto(produccion);
            dto.setDetalleProduccions(productionMapper.toProductionDetailResponseDtoList(produccion.getDetalleProduccions()));

            //ProduccionResponseDTO dto = convertProductionResponse(produccion);
            //dto.setDetalleProduccions(convertDetalleProductionResponse(produccion.getDetalleProduccions()));


            return dto;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProduccionResponseDTO> findAllProduccions()throws ExceptionNotFoundException {
        try {
            List<Produccion> produccions = produccionRepository.findAll();
            if (produccions.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }

            return productionMapper.toProductionResponseDtoList(produccions);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }





    /*private List<DetalleProduccionResponseDto> convertDetalleProductionResponse(List<DetalleProduccion> detalleProduccion){
        return detalleProduccion.stream().map(detalle -> {
            DetalleProduccionResponseDto detalleDto = new DetalleProduccionResponseDto();
            detalleDto.setId(detalle.getId());
            //detalleDto.setIngredienteId(detalle.getIngrediente().getId());
            detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setUnidad(detalle.getUnidad());
            return detalleDto;
        }).collect(Collectors.toList());
    }

    private ProductoResponseDTO convertProductoResponseDTO(Product product){
        ProductoResponseDTO responseDTO = new ProductoResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setImage_url(product.getImage());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStock(product.getStock());
        responseDTO.setStatus(product.getStatus());

        responseDTO.setCategoryId(product.getCategory().getId());
        responseDTO.setCategory_name(product.getCategory().getName());

        responseDTO.setBusinessId(product.getBusiness().getId());
        responseDTO.setBusiness_name(product.getBusiness().getName());

        responseDTO.setRecetaId(product.getReceta().getId());
        responseDTO.setReceta_name(product.getReceta().getName());
        responseDTO.setDetalleReceta(convertirDetallesRecetaADto(product.getReceta().getDetalleRecetas()));

        return responseDTO;
    }

    private List<DetalleRecetaResponseDto> convertirDetallesRecetaADto(List<DetalleRecetas> detallesRecetas) {
        return detallesRecetas.stream().map(detalle -> {
            DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setBaseId(detalle.getBase().getId());
            detalleDto.setNombre_base(detalle.getBase().getName());
            detalleDto.setDescription_base(detalle.getBase().getDescription());
            detalleDto.setDetalleBases(baseMapper.toDetailBaseResponseDtoList(detalle.getBase().getDetalleBases()));
            return detalleDto;
        }).collect(Collectors.toList());
    }

    private ProductionRecipeBaseResponseDto convertProductionRecipeBaseResponseDto(Produccion produccion, Receta receta, Base base){
        ProductionRecipeBaseResponseDto responseDto = new ProductionRecipeBaseResponseDto();
        responseDto.setId(produccion.getId());
        responseDto.setRecetaId(receta.getId());
        responseDto.setReceta_nombre(receta.getName());
        return responseDto;

    }*/
}
