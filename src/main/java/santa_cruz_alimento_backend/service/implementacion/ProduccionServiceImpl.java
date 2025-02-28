package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Response.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IProduccionRepository;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;

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

    @Override
    public Produccion calcularProduccion(Long productoId, double produccion_total) throws ExceptionNotFoundException {
        try {

            // Buscar el producto
            Product producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

            // Crear nueva lista de ingredientes ajustada
            List<ProduccionIngrediente> ingredientesAjustados = receta.getIngredientes().stream()
                    .map(ri -> {
                        ProduccionIngrediente nuevoPI = new ProduccionIngrediente();
                        nuevoPI.setId(ri.getId());
                        //nuevoPI.setProduccion();
                        nuevoPI.setIngrediente(ri.getIngrediente());
                        nuevoPI.setUnidad(ri.getUnidad());
                        double nuevaCantidad = (produccion_total / producto.getStock()) * ri.getCantidad();
                        nuevoPI.setCantidad(nuevaCantidad);
                        return nuevoPI;
                    })
                    .collect(Collectors.toList());

            // Crear una nueva instancia de Produccion con los ingredientes ajustados y el producto asociado
            Produccion produccion = new Produccion();
            produccion.setId(produccion.getId());
            produccion.setSolicitud_produccion(produccion_total);
            produccion.setProducido(produccion.getProducido());
            produccion.setComentario(produccion.getComentario());
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setProducto(producto); // Relacionar con el producto
            produccion.setIngredientes(ingredientesAjustados);

            return produccion;
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Produccion registrarProduccion(Long productoId, double solicitud_producion) throws ExceptionNotFoundException {
        try {

            // Buscar el producto
            Product producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new ExceptionNotFoundException("Producto no encontrado"));

            // Buscar la receta asociada al producto
            Receta receta = recetaRepository.findById(producto.getReceta().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada"));

            // Crear una nueva instancia de Produccion
            Produccion produccion = new Produccion();
            produccion.setSolicitud_produccion(solicitud_producion);
            produccion.setProducido((int) solicitud_producion);  // Inicialmente en 0
            produccion.setComentario("Nueva producción registrada");
            produccion.setFechaProduccion(new Timestamp(System.currentTimeMillis()));
            produccion.setProducto(producto); // Relacionar con el producto

            // Crear nueva lista de ingredientes ajustada
            List<ProduccionIngrediente> ingredientesAjustados = receta.getIngredientes().stream()
                    .map(ri -> {

                    Ingrediente ingrediente = ingredienteRepository.findById(ri.getIngrediente().getId())
                            .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado"));

                        ProduccionIngrediente nuevoPI = new ProduccionIngrediente();
                        nuevoPI.setIngrediente(ri.getIngrediente());
                        nuevoPI.setUnidad(ri.getUnidad());
                        double nuevaCantidad = (solicitud_producion / producto.getStock()) * ri.getCantidad();

                        if (ingrediente.getCantidad() >= nuevaCantidad){
                            /*ingrediente.setCantidad(ingrediente.getCantidad() - ri.getCantidad());
                            ingrediente.setCantidad(ingrediente.getCantidad() - nuevoPI.getCantidad());*/
                            ingrediente.setCantidad(ingrediente.getCantidad() - nuevaCantidad);
                        }else {
                            throw new ExceptionNotFoundException("¡Cantidad de Ingrediente Insuficiente!, Para la Produccion");

                        }

                        nuevoPI.setCantidad(nuevaCantidad);
                        nuevoPI.setProduccion(produccion); // Establecer la relación correcta


                        return nuevoPI;
                    })
                    .collect(Collectors.toList());

            // Asignar ingredientes ajustados a la producción
            produccion.setIngredientes(ingredientesAjustados);

            // Guardar la producción y en cascada los ingredientes
            return produccionRepository.save(produccion);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

//    @Override
//    public Produccion editarProduccionById(Long produccionId, Produccion produccionActualizada) {
//        // Buscar la producción existente
//        Produccion produccion = produccionRepository.findById(produccionId)
//                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));
//
//        // Obtener el producto asociado
//        Product producto = produccion.getProducto();
//
//        // Obtener la receta asociada al producto
//        Receta receta = recetaRepository.findById(producto.getReceta().getId())
//                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
//
//        // Actualizar la lista de ingredientes ajustados según la receta
//        List<ProduccionIngrediente> ingredientesAjustados = receta.getIngredientes().stream()
//                .map(ri -> {
//                    ProduccionIngrediente nuevoPI = new ProduccionIngrediente();
//                    nuevoPI.setIngrediente(ri.getIngrediente());
//                    nuevoPI.setUnidad(ri.getUnidad());
//                    nuevoPI.setCantidad(ri.getCantidad()); // Mantiene la cantidad original de la receta
//                    return nuevoPI;
//                })
//                .collect(Collectors.toList());
//
//        // Actualizar la producción existente con los nuevos valores
//        produccion.setSolicitud_produccion(produccionActualizada.getSolicitud_produccion());
//        produccion.setProducido(produccionActualizada.getProducido());
//        produccion.setComentario(produccionActualizada.getComentario());
//        produccion.setIngredientes(ingredientesAjustados);
//
//        return produccionRepository.save(produccion);
//    }

    @Override
    public Produccion editarProduccionById(Long produccionId, Produccion produccion) {
        // Buscar la producción existente
        Produccion produccionExistente = produccionRepository.findById(produccionId)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));

        // Actualizar los campos de la producción existente con los nuevos valores
        produccionExistente.setSolicitud_produccion(produccion.getSolicitud_produccion());
        produccionExistente.setProducido(produccion.getProducido());  // Actualizar producido si es necesario
        produccionExistente.setComentario(produccion.getComentario()); // Actualizar comentario
        produccionExistente.setProducto(produccion.getProducto()); // Relacionar nuevamente con el producto

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
        produccionExistente.setIngredientes(produccion.getIngredientes());

        // Guardar la producción actualizada
        return produccionRepository.save(produccionExistente);
    }



//    @Override
//    public Produccion editarProduccionById(Long produccionId, Produccion produccion) {
//        // Buscar la producción existente
//        Produccion produccionExistente = produccionRepository.findById(produccionId)
//                .orElseThrow(() -> new RuntimeException("Producción no encontrada"));
//
//        // Actualizar campos básicos
//        produccionExistente.setComentario(produccion.getComentario());
//        produccionExistente.setSolicitud_produccion(produccion.getSolicitud_produccion());
//        produccionExistente.setProducido(produccion.getProducido());
//
//        // Gestionar ingredientes
//        if (produccion.getIngredientes() != null && !produccion.getIngredientes().isEmpty()) {
//            // Eliminar ingredientes existentes
//            produccionExistente.getIngredientes().forEach(pi -> {
//                produccionIngredienteRepository.delete(pi);
//            });
//
//            // Crear nuevos ingredientes
//            List<ProduccionIngrediente> nuevosIngredientes = produccion.getIngredientes().stream()
//                    .map(ing -> {
//                        ProduccionIngrediente nuevo = new ProduccionIngrediente();
//                        nuevo.setIngrediente(ing.getIngrediente());
//                        nuevo.setUnidad(ing.getUnidad());
//                        nuevo.setCantidad(ing.getCantidad());
//                        nuevo.setProduccion(produccionExistente);
//                        return nuevo;
//                    })
//                    .collect(Collectors.toList());
//
//            produccionExistente.setIngredientes(nuevosIngredientes);
//        }
//
//        return produccionRepository.save(produccionExistente);
//    }

    @Override
    public ProduccionResponseDTO getByProduccionId(Long id)  throws ExceptionNotFoundException{
        try {

            Produccion produccion = produccionRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Produccion no encontrado con id: " + id));
            ProduccionResponseDTO produccionResponseDTO = new ProduccionResponseDTO();
            produccionResponseDTO.setId(id);
            produccionResponseDTO.setSolicitud_proudcion(produccion.getSolicitud_produccion());
            produccionResponseDTO.setProducido(produccion.getProducido());
            produccionResponseDTO.setComentario(produccion.getComentario());
            produccionResponseDTO.setFecha_produccion(produccion.getFechaProduccion());
            produccionResponseDTO.setProductoId(produccion.getProducto().getId());
            produccionResponseDTO.setProducto_name(produccion.getProducto().getName());
            return produccionResponseDTO;
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProduccionResponseDTO> findAllProduccions()throws ExceptionNotFoundException {
        try {
            return produccionRepository.findAll().stream().map(Produccion::produccionDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
