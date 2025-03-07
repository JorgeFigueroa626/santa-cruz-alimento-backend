package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImpl implements IIngredienteService {

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Override
    public Ingrediente save(IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setName(requestDTO.getName());
            ingrediente.setCantidad(requestDTO.getCantidad());
            ingrediente.setUnidad(requestDTO.getUnidad());
            return ingredienteRepository.save(ingrediente);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Ingrediente getById(Long id) throws ExceptionNotFoundException {
        return ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el id: " + id));
    }

    @Override
    public List<IngredientesResponseDto> findAll() throws ExceptionNotFoundException {
        try {

            return ingredienteRepository.findAll().stream()
                    .map(i -> new IngredientesResponseDto(i.getId(),i.getName(),i.getCantidad(), i.getUnidad()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Ingrediente updateById(Long id, Ingrediente ingrediente) throws ExceptionNotFoundException {
        try {
            Ingrediente ingredienteId = ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el id: " + id));
            ingredienteId.setName(ingrediente.getName());
            ingredienteId.setCantidad(ingrediente.getCantidad());
            ingredienteId.setUnidad(ingrediente.getUnidad());
            return ingredienteRepository.save(ingredienteId);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            ingredienteRepository.deleteById(id);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
