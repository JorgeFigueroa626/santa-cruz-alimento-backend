package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;
import santa_cruz_alimento_backend.entity.model.Ingrediente;

import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IDetalleComprasRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;



import java.util.List;
/*
@Service
public class InventarioServiceImpl implements IInventarioService {

    @Autowired
    private IInventarioRepository inventarioRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IDetalleComprasRepository detalleCompraRepository;

    // Consultar el inventario de un ingrediente específico
    public Inventario getInventarioByIngrediente(Long ingredienteId) throws ExceptionNotFoundException {
        return inventarioRepository.findByIngredienteId(ingredienteId)
                .orElseThrow(() -> new ExceptionNotFoundException("Inventario no encontrado para el ingrediente con ID: " + ingredienteId));
    }

    // Agregar una cantidad de ingrediente al inventario (esto se puede hacer a través de las compras)
    public Inventario agregarAlInventario(Long ingredienteId, Double cantidad) throws ExceptionNotFoundException {
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId)
                .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado"));

        Inventario inventario = inventarioRepository.findByIngredienteId(ingredienteId)
                .orElse(new Inventario()); // Si no existe, creamos un nuevo inventario

        if (inventario.getIngrediente() == null) {
            inventario.setIngrediente(ingrediente);  // Establecemos el ingrediente en el inventario
        }

        inventario.setCantidad(inventario.getCantidad() + cantidad); // Sumamos la cantidad al inventario existente
        return inventarioRepository.save(inventario);
    }

    // Reducir la cantidad del inventario cuando se utilice el ingrediente
    public Inventario reducirInventario(Long ingredienteId, Double cantidad) throws ExceptionNotFoundException {
        Inventario inventario = getInventarioByIngrediente(ingredienteId);

        if (inventario.getCantidad() < cantidad) {
            throw new RuntimeException("Cantidad insuficiente en inventario");
        }

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        return inventarioRepository.save(inventario);
    }

    // Agregar al inventario los ingredientes adquiridos en una compra
    public void agregarIngredientesDeCompra(Compra compra) throws ExceptionNotFoundException {
        for (DetalleCompra detalleCompra : compra.getDetalleCompras()) {
            Long ingredienteId = detalleCompra.getIngrediente().getId();
            Double cantidadComprada = detalleCompra.getCantidad();

            // Agregar la cantidad comprada al inventario
            agregarAlInventario(ingredienteId, cantidadComprada);
        }
    }

    // Consultar todo el inventario de ingredientes
    public List<Inventario> obtenerTodoInventario() throws ExceptionNotFoundException {
        return inventarioRepository.findAll();
    }
}*/
