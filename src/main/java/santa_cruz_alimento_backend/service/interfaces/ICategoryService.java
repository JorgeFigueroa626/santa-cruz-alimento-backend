package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.entity.model.Category;

import java.util.List;

@Service
public interface ICategoryService {

    Category save(Category category);

    Category getById(Long id);

    List<Category> findAll();

    Category updateById(Long id, Category category);

    void deleteById(Long id);
}
