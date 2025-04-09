package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.rol.RolRequestDto;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.service.interfaces.IRolService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class RolController {

    @Autowired
    private IRolService rolService;

    @PostMapping(ROL)
    public BaseResponse saveRol(@RequestBody RolRequestDto rol) {
        Rol save  = rolService.saveRol(rol);
        return new BaseResponse(true, save, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(ALL_ROL)
    public BaseResponse findAllRol() {
        List<Rol> rolList = rolService.findAllRol();
        return new BaseResponse(true, rolList, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(BY_ROL_ID)
    public BaseResponse getByRolId(@PathVariable Long id) {
        Rol rol = rolService.getByRolId(id);
        return  new BaseResponse(true, rol, ReplyMessage.MESSAGE_BY);
    }

    @PutMapping(BY_ROL_ID)
    public BaseResponse updateByRolId(@PathVariable Long id, @RequestBody RolRequestDto rol) {
        Rol update = rolService.updateByRolId(id, rol);
        return  new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);

    }

    @DeleteMapping(BY_ROL_ID)
    public BaseResponse deleteByRolId(@PathVariable Long id) {
        rolService.deleteByRolId(id);
        return  new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
