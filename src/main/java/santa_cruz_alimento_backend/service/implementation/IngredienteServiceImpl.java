package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImpl implements IIngredienteService {

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(IngredienteServiceImpl.class);

    @Override
    public Ingrediente save(IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a registro: {}",requestDTO);

            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setName(requestDTO.getName());
            ingrediente.setCantidad(requestDTO.getCantidad());
            ingrediente.setStock(0.0);
            ingrediente.setUnidad(requestDTO.getUnidad());
            ingrediente.setStatus(ReplyStatus.ACTIVE.getValue());
            Ingrediente save = ingredienteRepository.save(ingrediente);

            logger.info("Ingrediente registrado : {}", ingrediente);
            return save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Ingrediente getById(Long id) throws ExceptionNotFoundException {
        try {
            return ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<IngredientesResponseDto> findAll() throws ExceptionNotFoundException {
        try {

            return ingredienteRepository.findAll().stream()
                    .map(i -> new IngredientesResponseDto(i.getId(),i.getName(),i.getCantidad(),i.getStock(),i.getUnidad(), i.getStatus()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Ingrediente updateById(Long id, IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de modificar: {}",requestDTO);

            Ingrediente ingrediente = ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + id));
            ingrediente.setName(requestDTO.getName());
            ingrediente.setCantidad(requestDTO.getCantidad());
            ingrediente.setStock(requestDTO.getStock());
            ingrediente.setUnidad(requestDTO.getUnidad());
            ingrediente.setStatus(requestDTO.getStatus());
            Ingrediente update = ingredienteRepository.save(ingrediente);

            logger.info("Ingrediente modificado : {}", update);
            return update;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Ingrediente ingrediente = ingredienteRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + id));
            ingrediente.setStatus(ReplyStatus.INACTIVE.getValue());
            ingredienteRepository.save(ingrediente);
            logger.info("Ingrediente eleminado : {}", ingrediente);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
