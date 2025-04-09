package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.category.CategoryRequestDto;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.service.interfaces.ICategoryService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @PostMapping(CATEGORY)
    public BaseResponse save(@RequestBody CategoryRequestDto category)   {
            Category save =  categoryService.save(category);
            return new BaseResponse(true, save, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(ALL_CATEGORY)
    public BaseResponse findAll()  {
        var categorys = categoryService.findAll();
        return new BaseResponse(true, categorys, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(BY_CATEGORY_ID)
    public BaseResponse getById(@PathVariable Long id)   {
        var category = categoryService.getById(id);
        return new BaseResponse(true, category, ReplyMessage.MESSAGE_BY);
    }

    @PutMapping(BY_CATEGORY_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody CategoryRequestDto category)  {
        Category categoryUpdate = categoryService.updateById(id, category);
        return new BaseResponse(true, categoryUpdate, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_CATEGORY_ID)
    public BaseResponse deleteById(@PathVariable Long id)  {
        categoryService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
