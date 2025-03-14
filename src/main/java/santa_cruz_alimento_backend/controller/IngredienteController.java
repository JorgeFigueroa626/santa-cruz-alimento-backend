package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.response.IngredientesResponseDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class IngredienteController {

    @Autowired
    private IIngredienteService ingredienteService;

    @PostMapping(INGREDIENTE)
    public BaseResponse save(@RequestBody IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException {
        Ingrediente save = ingredienteService.save(ingrediente);
        return new BaseResponse(true, save, MESSAGE_SAVE);

    }

    @GetMapping(BY_INGREDIENTE_ID)
    public BaseResponse getById(@PathVariable Long id) throws ExceptionNotFoundException{
        Ingrediente ingrediente = ingredienteService.getById(id);
        return new BaseResponse(true, ingrediente, MESSAGE_BY);
    }

    @GetMapping(ALL_INGREDIENTE)
    public BaseResponse findAll() throws ExceptionNotFoundException{
        List<IngredientesResponseDto> list = ingredienteService.findAll();
        return  new BaseResponse(true, list, MESSAGE_LIST);
    }

    @PutMapping(BY_INGREDIENTE_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException{
        Ingrediente update = ingredienteService.updateById(id, ingrediente);
        return new BaseResponse(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_INGREDIENTE_ID)
    public BaseResponse deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        ingredienteService.deleteById(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
