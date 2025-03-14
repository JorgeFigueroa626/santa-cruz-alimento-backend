package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.request.CategoryRequestDto;
import santa_cruz_alimento_backend.dto.response.CategoryResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;


import java.util.List;

@Service
public interface ICategoryService {

    Category save(CategoryRequestDto category) throws ExceptionNotFoundException;

    Category getById(Long id) throws ExceptionNotFoundException;

    List<CategoryResponseDTO> findAll() throws ExceptionNotFoundException;

    List<Category> listarCategoriasConFiltros(String text, Integer page, Integer size);

    Category updateById(Long id, CategoryRequestDto category) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
