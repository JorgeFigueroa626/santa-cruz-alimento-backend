package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.RolRequestDto;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IRolService;
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
    public BaseResponse saveRol(@RequestBody RolRequestDto rol) throws ExceptionNotFoundException {
        Rol save  = rolService.saveRol(rol);
        return new BaseResponse(true, save, MESSAGE_SAVE);
    }

    @GetMapping(ALL_ROL)
    public BaseResponse findAllRol() throws ExceptionNotFoundException{
        List<Rol> rolList = rolService.findAllRol();
        return new BaseResponse(true, rolList, MESSAGE_LIST);
    }

    @GetMapping(BY_ROL_ID)
    public BaseResponse getByRolId(@PathVariable Long id) throws ExceptionNotFoundException{
        Rol rol = rolService.getByRolId(id);
        return  new BaseResponse(true, rol, MESSAGE_BY);
    }

    @PutMapping(BY_ROL_ID)
    public BaseResponse updateByRolId(@PathVariable Long id, @RequestBody RolRequestDto rol) throws ExceptionNotFoundException{
        Rol update = rolService.updateByRolId(id, rol);
        return  new BaseResponse(true, update, MESSAGE_UPDATE);

    }

    @DeleteMapping(BY_ROL_ID)
    public BaseResponse deleteByRolId(@PathVariable Long id) throws ExceptionNotFoundException{
        rolService.deleteByRolId(id);
        return  new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
