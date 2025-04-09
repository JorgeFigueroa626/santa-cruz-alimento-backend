package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.request.category.CategoryRequestDto;
import santa_cruz_alimento_backend.dto.response.category.CategoriaResponseDto;
import santa_cruz_alimento_backend.dto.response.category.CategoryResponseDTO;
import santa_cruz_alimento_backend.dto.response.category.SubProductoResponseDto;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.DetalleSubProducto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.ICategoryMapper;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ICategoryMapper categoryMapper;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Transactional
    @Override
    public Category save(CategoryRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);

            Category category = new Category();
            category.setName(requestDto.getName());
            category.setStatus(ReplyStatus.ACTIVE.getValue());
            category.setUnidad(1);
            category.setStock(0);

            List<DetalleSubProducto> subProductos = requestDto.getDetalleSubProductRequestDto().stream().map(iDto->{
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

                DetalleSubProducto subProducto = new DetalleSubProducto();
                subProducto.setIngrediente(ingrediente);
                subProducto.setCantidad(iDto.getCantidad());
                subProducto.setUnidad(iDto.getUnidad());
                subProducto.setCategory(category);
                return subProducto;
            }).collect(Collectors.toList());
            category.setDetalleSubProductos(subProductos);

            Category save = categoryRepository.save(category);
            logger.info("Categoria registrado: {}", save);
            return save;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public CategoriaResponseDto getById(Long id) throws ExceptionNotFoundException {
        try {
            //return categoryRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
            CategoriaResponseDto responseDto = categoryMapper.toCategoryResponseDto(category);
            responseDto.setDetalleSubProductos(categoryMapper.toDetailSubProductoResponseDtoList(category.getDetalleSubProductos()));
            return  responseDto;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<CategoriaResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            // Obtener todas las categorías desde el repositorio
            List<Category> categories = categoryRepository.findAll();

            // Si no se encuentran categorías, lanzamos una excepción
            if (categories.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }

            return categoryMapper.toCategoryResponseDtoList(categories);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
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

    @Transactional
    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            // Buscar la categoría por su ID
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + id));
            category.setStatus(ReplyStatus.INACTIVE.getValue());
            categoryRepository.save(category);
            logger.info("Categoria eliminado : {}", category);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
