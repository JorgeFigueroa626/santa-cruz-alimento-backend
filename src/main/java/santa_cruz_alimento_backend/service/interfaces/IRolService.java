package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IRolService {

    Rol saveRol(Rol rol) throws ExceptionNotFoundException;

    Rol getByRolId(Long id) throws ExceptionNotFoundException;

    Rol updateByRolId(Long id, Rol rol) throws ExceptionNotFoundException;

    List<Rol> findAllRol() throws ExceptionNotFoundException;

    void deleteByRolId(Long id) throws ExceptionNotFoundException;


}
