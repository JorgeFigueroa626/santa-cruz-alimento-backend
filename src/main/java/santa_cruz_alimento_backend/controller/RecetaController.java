package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.Response.IngredienteDTO;
import santa_cruz_alimento_backend.dto.Request.RecetaRequestDTO;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.io.IOException;
import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class RecetaController {

    @Autowired
    private IRecetaService recetaService;

    //    XXXX
    //@PostMapping(RECETA)
    public ResponseEntity<?> save(@RequestBody Receta recetaDto) throws IOException {
        boolean success = recetaService.addReceta(recetaDto);
        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(RECETA)
    public JsonResult create(@RequestBody RecetaRequestDTO dto) throws ExceptionNotFoundException {
        Receta receta = recetaService.createReceta(dto);
        return new JsonResult(true, receta, MESSAGE_SAVE);
    }

    @GetMapping(ALL_RECETA)
    public JsonResult findAll() throws ExceptionNotFoundException{
        List<Receta> recetas = recetaService.findAll();
        return new JsonResult(true, recetas, MESSAGE_LIST);
    }

    @GetMapping(BY_RECETA_ID)
    public JsonResult getById(@PathVariable Long id) throws ExceptionNotFoundException{
        Receta receta = recetaService.getByRecetaId(id);
        return new JsonResult(true, receta, MESSAGE_BY);
    }

    @GetMapping(BY_NAME_RECETA)
    public JsonResult getIngredienteByNameReceta(@PathVariable String nombre) throws ExceptionNotFoundException{
        List<IngredienteDTO> ingredienteDTOS = recetaService.getRecetaByNombre(nombre);
        return new JsonResult(true, ingredienteDTOS, MESSAGE_LIST);
    }

    @PutMapping(BY_RECETA_ID)
    public JsonResult editarRecetaById(@PathVariable Long id, @RequestBody RecetaRequestDTO requestDTO) throws ExceptionNotFoundException{
        Receta update = recetaService.updateById(id, requestDTO);
        return new JsonResult(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_RECETA_ID)
    public JsonResult deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        recetaService.deleteById(id);
        return new JsonResult(true, null, MESSAGE_DELETE);
    }
}
