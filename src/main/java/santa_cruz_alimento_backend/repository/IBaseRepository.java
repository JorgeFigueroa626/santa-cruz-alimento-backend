package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.Base;
import santa_cruz_alimento_backend.entity.model.Venta;

import java.util.List;

@Repository
public interface IBaseRepository extends JpaRepository<Base, Long> {
    //List<Base> findByProductoId(Long productId);
}
