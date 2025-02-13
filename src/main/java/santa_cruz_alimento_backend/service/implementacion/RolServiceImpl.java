package santa_cruz_alimento_backend.service.implementacion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.repository.IRolRepository;
import santa_cruz_alimento_backend.service.interfaces.IRolService;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public Rol saveRol(Rol rol) {
        try {
            return rolRepository.save(rol);
        }catch ( Exception e){
            throw new RuntimeException("Error al crear el Rol ", e);
        }

    }

    @Override
    public Rol getByRolId(Long id) {
        return rolRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }

    @Override
    public Rol updateByRolId(Long id, Rol rol) {
        try {
            Rol updateId = rolRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
            updateId.setName(rol.getName());
            return rolRepository.save(updateId);
        }catch (Exception e){
            throw new RuntimeException("Error al actualizar el Rol con id: " + id, e);
        }

    }

    @Override
    public List<Rol> findAllRol() {
        try {
            return rolRepository.findAll();
        }catch ( Exception e){
            throw new RuntimeException("Lista vacias", e);
        }

    }

    @Override
    public void deleteByRolId(Long id) {
        try {
            rolRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("Error al eliminar el Rol con id: " + id, e);
        }

    }
}
