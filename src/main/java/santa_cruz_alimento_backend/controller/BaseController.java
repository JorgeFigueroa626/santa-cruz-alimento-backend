package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.base.BaseRequestDto;
import santa_cruz_alimento_backend.service.interfaces.IBaseService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class BaseController {

    @Autowired
    private IBaseService baseService;

    @GetMapping(ALL_BASE)
    public BaseResponse ListBase(){
        var list = baseService.ListBase();
        return new BaseResponse(true, list, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(BY_BASE_ID)
    public BaseResponse GetByIdBase(@PathVariable Long id){
        var response = baseService.GetBaseById(id);
        return new BaseResponse(true, response, ReplyMessage.MESSAGE_BY);
    }

    @PostMapping(BASE)
    public BaseResponse registerBase(@RequestBody BaseRequestDto requestDto){
        var response = baseService.registerBase(requestDto);
        return new BaseResponse(true, response, ReplyMessage.MESSAGE_SAVE);
    }

    @PutMapping(BY_BASE_ID)
    public BaseResponse updateBaseById(@PathVariable Long id, @RequestBody BaseRequestDto requestDto){
        var response = baseService.updateBaseById(id, requestDto);
        return new BaseResponse(true, response, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_BASE_ID)
    public BaseResponse deleteBaseById(@PathVariable Long id){
        baseService.deleteBaseById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
