package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.ingredient.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class IngredienteController {

    @Autowired
    private IIngredienteService ingredienteService;

    @PostMapping(INGREDIENTE)
    public BaseResponse save(@RequestBody IngredienteRequestDTO ingrediente)  {
        var save = ingredienteService.save(ingrediente);
        return new BaseResponse(true, save, ReplyMessage.MESSAGE_SAVE);

    }

    @GetMapping(BY_INGREDIENTE_ID)
    public BaseResponse getById(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteService.getById(id);
        return new BaseResponse(true, ingrediente, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(ALL_INGREDIENTE)
    public BaseResponse findAll() {
        List<IngredientesResponseDto> list = ingredienteService.findAll();
        return  new BaseResponse(true, list, ReplyMessage.MESSAGE_LIST);
    }

    @PutMapping(BY_INGREDIENTE_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody IngredienteRequestDTO ingrediente) {
        var update = ingredienteService.updateById(id, ingrediente);
        return new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_INGREDIENTE_ID)
    public BaseResponse deleteById(@PathVariable Long id) {
        ingredienteService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
