package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;

import java.util.List;

@Service
public class IngredienteServiceImpl implements IIngredienteService {

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    @Override
    public Ingrediente getById(Long id) {
        return ingredienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con el id: " + id));
    }

    @Override
    public List<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    @Override
    public Ingrediente updateById(Long id, Ingrediente ingrediente) {
        Ingrediente ingredienteId = ingredienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingrediente no encontrado con el id: " + id));
        ingredienteId.setName(ingrediente.getName());
        ingredienteId.setUnidad(ingrediente.getUnidad());
        ingredienteId.setStock(ingrediente.getStock());
        return ingredienteRepository.save(ingredienteId);
    }

    @Override
    public void deleteById(Long id) {
        ingredienteRepository.deleteById(id);
    }
}
