package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;
import santa_cruz_alimento_backend.entity.dto.IngredienteResponseDTO;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.service.interfaces.IIngredienteService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;


@RestController
@RequestMapping(API)
public class IngredienteController {

    @Autowired
    private IIngredienteService ingredienteService;

    @PostMapping(INGREDIENTE)
    public ResponseEntity<?> save(@RequestBody Ingrediente ingrediente){
        try {
            return ResponseEntity.ok(ingredienteService.save(ingrediente));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(BY_INGREDIENTE_ID)
    public ResponseEntity<Ingrediente> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ingredienteService.getById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ALL_INGREDIENTE)
    public ResponseEntity<List<IngredienteResponseDTO>> findAll(){
        try {
            return ResponseEntity.ok(ingredienteService.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(BY_INGREDIENTE_ID)
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Ingrediente ingrediente){
        try {
            return ResponseEntity.ok(ingredienteService.updateById(id, ingrediente));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(BY_INGREDIENTE_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            ingredienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
