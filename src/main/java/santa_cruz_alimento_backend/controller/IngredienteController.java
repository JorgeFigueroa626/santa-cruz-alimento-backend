package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.Request.IngredienteRequestDTO;
import santa_cruz_alimento_backend.dto.Response.IngredienteResponseDTO;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class IngredienteController {

    @Autowired
    private IIngredienteService ingredienteService;

    @PostMapping(INGREDIENTE)
    public JsonResult save(@RequestBody IngredienteRequestDTO ingrediente) throws ExceptionNotFoundException {
        Ingrediente save = ingredienteService.save(ingrediente);
        return new JsonResult(true, save, MESSAGE_SAVE);

    }

    @GetMapping(BY_INGREDIENTE_ID)
    public JsonResult getById(@PathVariable Long id) throws ExceptionNotFoundException{
        Ingrediente ingrediente = ingredienteService.getById(id);
        return new JsonResult(true, ingrediente, MESSAGE_BY);
    }

    @GetMapping(ALL_INGREDIENTE)
    public JsonResult findAll() throws ExceptionNotFoundException{
        List<IngredienteResponseDTO> list = ingredienteService.findAll();
        return  new JsonResult(true, list, MESSAGE_LIST);
    }

    @PutMapping(BY_INGREDIENTE_ID)
    public JsonResult updateById(@PathVariable Long id, @RequestBody Ingrediente ingrediente) throws ExceptionNotFoundException{
        Ingrediente update = ingredienteService.updateById(id, ingrediente);
        return new JsonResult(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_INGREDIENTE_ID)
    public JsonResult deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        ingredienteService.deleteById(id);
        return new JsonResult(true, null, MESSAGE_DELETE);
    }
}
