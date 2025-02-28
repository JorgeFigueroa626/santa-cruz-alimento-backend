package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import santa_cruz_alimento_backend.dto.Response.CategoryResponseDTO;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public Category save(Category category) throws ExceptionNotFoundException {
        try {
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
    public List<Category> findAll() throws ExceptionNotFoundException {
        try {
            return categoryRepository.findAll();
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
                    .map(category -> new Category(category.getId(), category.getName()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public Category updateById(Long id, Category category) throws ExceptionNotFoundException {
        try {
            Category categoryId = categoryRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Category no encontrado con id: " + id));
            categoryId.setName(category.getName());
            return categoryRepository.save(categoryId);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
