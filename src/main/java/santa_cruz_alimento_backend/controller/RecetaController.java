package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;
import santa_cruz_alimento_backend.entity.dto.RecetaDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;

import java.io.IOException;
import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

@RestController
@RequestMapping(API)
public class RecetaController {

    @Autowired
    private IRecetaService recetaService;

    //    XXXX
    @PostMapping(RECETA)
    public ResponseEntity<?> save(@RequestBody Receta recetaDto) throws IOException {
        boolean success = recetaService.addReceta(recetaDto);
        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(RECETAS)
    public ResponseEntity<Receta> create(@RequestBody RecetaDto dto) {
        return ResponseEntity.ok(recetaService.createReceta(dto));
    }

    @GetMapping(ALL_RECETA)
    public ResponseEntity<List<?>> findAll(){
        return ResponseEntity.ok(recetaService.findAll());
    }

    @GetMapping(BY_RECETA_ID)
    public ResponseEntity<?> getById(@PathVariable Long id){
        return ResponseEntity.ok(recetaService.getByRecetaId(id));
    }

    @GetMapping(BY_NAME_RECETA)
    public ResponseEntity<List<IngredienteDTO>> getIngredienteByNameReceta(@PathVariable String nombre){
        return ResponseEntity.ok(recetaService.getRecetaByNombre(nombre));
    }

    @DeleteMapping(BY_RECETA_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        recetaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
