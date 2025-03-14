package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.request.CategoryRequestDto;
import santa_cruz_alimento_backend.dto.response.CategoryResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Category save(CategoryRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            Category category = new Category();
            category.setName(requestDto.getName());
            category.setStatus(ReplyStatus.ACTIVO);
            return categoryRepository.save(category);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Category getById(Long id) throws ExceptionNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Category no encontrado con id: " + id));
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
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Category> listarCategoriasConFiltros(String text, Integer page, Integer size)throws ExceptionNotFoundException {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Category> categoryPage;
            if (text != null && !text.isEmpty()) {
                categoryPage = categoryRepository.findByNameContainingIgnoreCase(text, pageable);
            } else {
                categoryPage = categoryRepository.findAll(pageable);
            }
            return categoryPage.getContent().stream()
                    .map(category -> new Category(category.getId(), category.getName(), category.getStatus()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public Category updateById(Long id, CategoryRequestDto category) throws ExceptionNotFoundException {
        try {
            Category categoryId = categoryRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Category no encontrado con id: " + id));
            categoryId.setName(category.getName());
            categoryId.setStatus(category.getStatus());
            return categoryRepository.save(categoryId);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            // Buscar la categoría por su ID
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Categoría no encontrada"));

            // Cambiar el estado a INACTIVO
            category.setStatus(ReplyStatus.INACTIVO);

            // Guardar la categoría con el nuevo estado
            categoryRepository.save(category);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
