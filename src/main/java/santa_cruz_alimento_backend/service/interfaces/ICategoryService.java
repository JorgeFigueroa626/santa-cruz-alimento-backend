package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.request.category.CategoryRequestDto;
import santa_cruz_alimento_backend.dto.response.category.CategoriaResponseDto;
import santa_cruz_alimento_backend.dto.response.category.CategoryResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;


import java.util.List;

@Service
public interface ICategoryService {

    Category save(CategoryRequestDto category) throws ExceptionNotFoundException;

    CategoriaResponseDto getById(Long id) throws ExceptionNotFoundException;

    List<CategoriaResponseDto> findAll() throws ExceptionNotFoundException;

    Category updateById(Long id, CategoryRequestDto category) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
