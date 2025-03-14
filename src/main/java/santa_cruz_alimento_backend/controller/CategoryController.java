package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.CategoryRequestDto;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;


import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;


@RestController
@RequestMapping(API)
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping(CATEGORY)
    public BaseResponse save(@RequestBody CategoryRequestDto category) throws ExceptionNotFoundException {
            Category save =  categoryService.save(category);
            return new BaseResponse(true, save, MESSAGE_SAVE);
    }

    @GetMapping(ALL_CATEGORY)
    public BaseResponse findAll() throws ExceptionNotFoundException{
        var categorys = categoryService.findAll();
        return new BaseResponse(true, categorys, MESSAGE_LIST);
    }

    @GetMapping(ALL_CATEGORY_FILTERS)
    public BaseResponse getAllCategories(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(required = false) String text
    ) throws ExceptionNotFoundException {
        List<Category> category = categoryService.listarCategoriasConFiltros(text, page, size);
        return new BaseResponse(true, category, MESSAGE_LIST);
    }


    @GetMapping(BY_CATEGORY_ID)
    public BaseResponse getById(@PathVariable Long id) throws ExceptionNotFoundException {
        Category category = categoryService.getById(id);
        return new BaseResponse(true, category, MESSAGE_BY);
    }

    @PutMapping(BY_CATEGORY_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody CategoryRequestDto category) throws ExceptionNotFoundException{
        Category categoryUpdate = categoryService.updateById(id, category);
        return new BaseResponse(true, categoryUpdate, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_CATEGORY_ID)
    public BaseResponse deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        categoryService.deleteById(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
