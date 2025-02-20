package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.Receta;

import java.util.Optional;

@Repository
public interface IRecetaRepository extends JpaRepository<Receta, Long> {

    Optional<Receta> findByName(String name);
}
