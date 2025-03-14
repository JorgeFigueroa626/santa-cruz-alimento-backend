package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.util.List;

@Repository
public interface IBusinessRepository extends JpaRepository<Business, Long> {
    List<Business> findByStatus(ReplyStatus status);
}
