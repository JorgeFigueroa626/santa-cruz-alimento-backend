package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

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
            ingrediente.setStock(0.0);
            ingrediente.setUnidad(requestDTO.getUnidad());
            ingrediente.setStatus(ReplyStatus.ACTIVO);
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
                    .map(i -> new IngredientesResponseDto(i.getId(),i.getName(),i.getCantidad(),i.getStock(),i.getUnidad(), i.getStatus()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Ingrediente updateById(Long id, IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            Ingrediente ingrediente = ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el id: " + id));
            ingrediente.setName(requestDTO.getName());
            ingrediente.setCantidad(requestDTO.getCantidad());
            ingrediente.setStock(requestDTO.getStock());
            ingrediente.setUnidad(requestDTO.getUnidad());
            ingrediente.setStatus(requestDTO.getStatus());
            return ingredienteRepository.save(ingrediente);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Ingrediente ingrediente = ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el id: " + id));
            ingrediente.setStatus(ReplyStatus.INACTIVO);
            ingredienteRepository.save(ingrediente);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
