package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.CompraDTO;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.ICompraRepository;
import santa_cruz_alimento_backend.repository.IDetalleComprasRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.ICompraService;

import java.sql.Timestamp;
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

    @Override
    public Compra createCompra(CompraDTO compraDTO) throws ExceptionNotFoundException {
        try {

            Compra compra = new Compra();
            compra.setFechaCompra(new Timestamp(System.currentTimeMillis()));

            List<DetalleCompra> detalleCompras = compraDTO.getDetalleCompras().stream().map(iDto ->{
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

                ingrediente.setCantidad(ingrediente.getCantidad() + iDto.getCantidad());

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

            return  compraRepository.save(compra);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public List<Compra> findAll() throws ExceptionNotFoundException {
        try {
            return compraRepository.findAll();
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Compra getByCompraId(Long id) throws ExceptionNotFoundException{
        try {
            return compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrado con id: " + id));
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    // Obtener todas las compras de un ingrediente específico
    public List<Compra> obtenerComprasPorIngrediente(Long ingredienteId)throws ExceptionNotFoundException {
        try {
            // Obtener todos los detalles de compra relacionados con el ingrediente
            List<DetalleCompra> detallesCompra = detalleCompraRepository.findByIngredienteId(ingredienteId);

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
    public boolean updateById(Long id, Compra compra) throws ExceptionNotFoundException {
        return false;
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            compraRepository.deleteById(id);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
