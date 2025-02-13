package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;

import java.util.List;

import static santa_cruz_alimento_backend.Constante.Constante.*;


@RestController
@RequestMapping(API)
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping(CATEGORY)
    public ResponseEntity<?> save(@RequestBody Category category){
        try {
            return ResponseEntity.ok(categoryService.save(category));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ALL_CATEGORY)
    public ResponseEntity<List<?>> findAll(){
        try {
            return ResponseEntity.ok(categoryService.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(BY_CATEGORY_ID)
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(categoryService.getById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(BY_CATEGORY_ID)
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Category category){
        try {
            return ResponseEntity.ok(categoryService.updateById(id, category));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(BY_CATEGORY_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
