package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.business.BusinessRequestDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class BusinessController {

    @Autowired
    private IBusinessService businessService;


    @PostMapping(BUSINESS)
    public BaseResponse save(@RequestBody BusinessRequestDto business)  {
        var save = businessService.save(business);
        return new BaseResponse(true, save, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(BY_BUSINESS_ID)
    public BaseResponse getById(@PathVariable Long id){
        Business business = businessService.getById(id);
        return new BaseResponse(true, business, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(ALL_BUSINESS)
    public BaseResponse findAll(){
        var businesses = businessService.findAll();
        return new BaseResponse(true, businesses, ReplyMessage.MESSAGE_LIST);
    }

    @PutMapping(BY_BUSINESS_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody BusinessRequestDto business) {
        var update = businessService.updateById(id, business);
        return new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_BUSINESS_ID)
    public BaseResponse deleteById(@PathVariable Long id){
         businessService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
