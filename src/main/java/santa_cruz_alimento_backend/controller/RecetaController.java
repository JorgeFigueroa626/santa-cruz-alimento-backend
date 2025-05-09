package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.recipe.RecetaRequesDto;
import santa_cruz_alimento_backend.dto.response.recipe.RecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class RecetaController {

    @Autowired
    private IRecetaService recetaService;

    @PostMapping(RECETA)
    public BaseResponse create(@RequestBody RecetaRequesDto dto)  {
        Receta receta = recetaService.createReceta(dto);
        return new BaseResponse(true, receta, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(ALL_RECETA)
    public BaseResponse findAll() {
        List<RecetaResponseDto> recetas = recetaService.findAll();
        return new BaseResponse(true, recetas, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(BY_RECETA_ID)
    public BaseResponse getById(@PathVariable Long id) {
        RecetaResponseDto receta = recetaService.getByRecetaId(id);
        return new BaseResponse(true, receta, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(BY_NAME_RECETA)
    public BaseResponse getIngredienteByNameReceta(@PathVariable String nombre) {
        var ingredientesResponseDtos = recetaService.getRecetaByNombre(nombre);
        return new BaseResponse(true, ingredientesResponseDtos, ReplyMessage.MESSAGE_LIST);
    }

    @PutMapping(BY_RECETA_ID)
    public BaseResponse editarRecetaById(@PathVariable Long id, @RequestBody RecetaRequesDto requestDTO) {
        Receta update = recetaService.updateById(id, requestDTO);
        return new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_RECETA_ID)
    public BaseResponse deleteById(@PathVariable Long id) {
        recetaService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
