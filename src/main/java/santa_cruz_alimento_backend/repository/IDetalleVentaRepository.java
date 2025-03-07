package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;
import santa_cruz_alimento_backend.entity.model.DetalleVenta;

import java.util.List;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    /// Método para encontrar todos los detalles de venta por producto
    List<DetalleVenta> findByProductoId(Long productoId);
}
