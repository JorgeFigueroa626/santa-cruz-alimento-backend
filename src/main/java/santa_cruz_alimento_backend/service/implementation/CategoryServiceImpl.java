package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.request.CategoryRequestDto;
import santa_cruz_alimento_backend.dto.response.CategoryResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category save(CategoryRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);

            Category category = new Category();
            category.setName(requestDto.getName());
            category.setStatus(ReplyStatus.ACTIVE.getValue());
            Category save = categoryRepository.save(category);

            logger.info("Categoria registrado: {}", save);
            return save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Category getById(Long id) throws ExceptionNotFoundException {
        try {
            return categoryRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<CategoryResponseDTO> findAll() throws ExceptionNotFoundException {
        try {
            // Obtener todas las categorías desde el repositorio
            List<Category> categories = categoryRepository.findAll();

            // Si no se encuentran categorías, lanzamos una excepción
            if (categories.isEmpty()) {
                throw new ExceptionNotFoundException("No categories found");
            }

            // Convertimos la lista de Category a una lista de CategoryResponseDTO
            return categories.stream()
                    .map(category -> new CategoryResponseDTO(
                            category.getId(),
                            category.getName(),
                            category.getStatus()
                    ))
                    .collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Category updateById(Long id, CategoryRequestDto category) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de modificar: {}",category);

            Category categoryId = categoryRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
            categoryId.setName(category.getName());
            categoryId.setStatus(category.getStatus());
            Category update = categoryRepository.save(categoryId);

            logger.info("Categoria modificado : {}", update);
            return update;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            // Buscar la categoría por su ID
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
            category.setStatus(ReplyStatus.INACTIVE.ordinal());
            categoryRepository.save(category);
            logger.info("Categoria eliminado : {}", category);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
