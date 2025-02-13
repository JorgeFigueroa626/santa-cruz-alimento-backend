package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Rol;

import java.util.List;

@Service
public interface IRolService {

    Rol saveRol(Rol rol);

    Rol getByRolId(Long id);

    Rol updateByRolId(Long id, Rol rol);

    List<Rol> findAllRol();

    void deleteByRolId(Long id);


}
