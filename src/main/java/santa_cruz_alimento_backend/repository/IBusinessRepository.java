package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.Business;

@Repository
public interface IBusinessRepository extends JpaRepository<Business, Long> {
}
