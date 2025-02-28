package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IRolService;
import santa_cruz_alimento_backend.util.shared.JsonResult;
import santa_cruz_alimento_backend.util.shared.ReplyMessage;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class RolController {

    @Autowired
    private IRolService rolService;

    @PostMapping(ROL)
    public JsonResult saveRol(@RequestBody Rol rol) throws ExceptionNotFoundException {
        Rol save  = rolService.saveRol(rol);
        return new JsonResult(true, save, MESSAGE_SAVE);
    }

    @GetMapping(ALL_ROL)
    public JsonResult findAllRol() throws ExceptionNotFoundException{
        List<Rol> rolList = rolService.findAllRol();
        return new JsonResult(true, rolList, MESSAGE_LIST);
    }

    @GetMapping(BY_ROL_ID)
    public JsonResult getByRolId(@PathVariable Long id) throws ExceptionNotFoundException{
        Rol rol = rolService.getByRolId(id);
        return  new JsonResult(true, rol, MESSAGE_BY);
    }

    @PutMapping(BY_ROL_ID)
    public JsonResult updateByRolId(@PathVariable Long id, @RequestBody Rol rol) throws ExceptionNotFoundException{
        Rol update = rolService.updateByRolId(id, rol);
        return  new JsonResult(true, update, MESSAGE_UPDATE);

    }

    @DeleteMapping(BY_ROL_ID)
    public JsonResult deleteByRolId(@PathVariable Long id) throws ExceptionNotFoundException{
        rolService.deleteByRolId(id);
        return  new JsonResult(true, null, MESSAGE_DELETE);
    }
}
