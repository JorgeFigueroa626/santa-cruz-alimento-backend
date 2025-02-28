package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.DetalleCompra;

import java.util.List;

@Repository
public interface IDetalleComprasRepository extends JpaRepository<DetalleCompra, Long> {
    // Método para encontrar todos los detalles de compra por ingrediente
    List<DetalleCompra> findByIngredienteId(Long ingredienteId);
}
