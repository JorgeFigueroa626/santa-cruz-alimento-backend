package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;


import java.util.List;

@Service
public interface ICategoryService {

    Category save(Category category) throws ExceptionNotFoundException;

    Category getById(Long id) throws ExceptionNotFoundException;

    List<Category> findAll() throws ExceptionNotFoundException;

    List<Category> listarCategoriasConFiltros(String text, Integer page, Integer size);

    Category updateById(Long id, Category category) throws ExceptionNotFoundException;

    void deleteById(Long id) throws ExceptionNotFoundException;
}
