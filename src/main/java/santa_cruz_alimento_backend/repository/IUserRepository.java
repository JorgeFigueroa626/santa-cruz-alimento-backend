package santa_cruz_alimento_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import santa_cruz_alimento_backend.entity.model.Usuario;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findFirstByCi(String ci);
    //Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
