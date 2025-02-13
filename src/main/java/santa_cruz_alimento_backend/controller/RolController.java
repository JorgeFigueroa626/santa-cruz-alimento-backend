package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.service.interfaces.IRolService;

import java.util.List;

import static santa_cruz_alimento_backend.Constante.Constante.*;

@RestController
@RequestMapping(API)
public class RolController {

    @Autowired
    private IRolService rolService;

    @PostMapping(ROL)
    public ResponseEntity<?> saveRol(@RequestBody Rol rol){
        try {
            return ResponseEntity.ok(rolService.saveRol(rol));
        }catch ( Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ALL_ROL)
    public ResponseEntity<List<?>> findAllRol(){
        return ResponseEntity.ok(rolService.findAllRol());
    }

    @GetMapping(BY_ROL_ID)
    public ResponseEntity<?> getByRolId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(rolService.getByRolId(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(BY_ROL_ID)
    public ResponseEntity<?> updateByRolId(@PathVariable Long id, @RequestBody Rol rol){
        try {
            return ResponseEntity.ok(rolService.updateByRolId(id, rol));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(BY_ROL_ID)
    public ResponseEntity<?> deleteByRolId(@PathVariable Long id){
        try {
            rolService.deleteByRolId(id);
            return ResponseEntity.noContent().build();
        }catch ( Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
