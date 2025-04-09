package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ingredient.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IIngredientMapper;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImpl implements IIngredienteService {

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IIngredientMapper ingredientMapper;

    private static final Logger logger = LoggerFactory.getLogger(IngredienteServiceImpl.class);

    @Transactional
    @Override
    public IngredientesResponseDto save(IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a registro: {}",requestDTO);

            Ingrediente ingrediente = ingredientMapper.toRequestDto(requestDTO);
            ingrediente.setStock(0.0);
            ingrediente.setStatus(ReplyStatus.ACTIVE.getValue());

            logger.info("Ingrediente registrado : {}", ingrediente);
            return ingredientMapper.toResponseDto(ingredienteRepository.save(ingrediente));
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
                    .map(ingredientMapper::toResponseDto)
                    .collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public IngredientesResponseDto updateById(Long id, IngredienteRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de modificar: {}",requestDTO);
            return ingredienteRepository.findById(id)
                    .map(exit ->{
                        Ingrediente ingrediente = ingredientMapper.toRequestDto(requestDTO);
                        ingrediente.setId(exit.getId());
                        logger.info("Ingrediente modificado : {}", ingrediente);
                        return ingredientMapper.toResponseDto(ingredienteRepository.save(ingrediente));
                    }).orElseThrow(()->new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
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
