package santa_cruz_alimento_backend.service.implementacion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IRolRepository;
import santa_cruz_alimento_backend.service.interfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Rol saveRol(Rol rol) throws ExceptionNotFoundException {
        try {
            return rolRepository.save(rol);
        }catch ( Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public Rol getByRolId(Long id) throws ExceptionNotFoundException {
        return rolRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Rol no encontrado con id: " + id));
    }

    @Override
    public Rol updateByRolId(Long id, Rol rol) throws ExceptionNotFoundException {
        try {
            Rol updateId = rolRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Rol no encontrado con id: " + id));
            updateId.setName(rol.getName());
            return rolRepository.save(updateId);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public List<Rol> findAllRol() throws ExceptionNotFoundException {
        try {
            return rolRepository.findAll();
        }catch ( Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public void deleteByRolId(Long id) throws ExceptionNotFoundException {
        try {
            rolRepository.deleteById(id);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }
}
