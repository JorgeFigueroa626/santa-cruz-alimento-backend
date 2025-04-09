package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.SubProduccion;

@Repository
public interface ISubProduccionRepository extends JpaRepository<SubProduccion, Long> {
}
