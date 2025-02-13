package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category no encontrado con id: " + id));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateById(Long id, Category category) {
        Category categoryId = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category no encontrado con id: " + id));
        categoryId.setName(category.getName());
        return categoryRepository.save(categoryId);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
