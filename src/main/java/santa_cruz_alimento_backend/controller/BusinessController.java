package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.BusinessRequestDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.ConstantEntity.*;
import static santa_cruz_alimento_backend.util.message.ReplyMessage.*;


@RestController
@RequestMapping(API)
public class BusinessController {

    @Autowired
    private IBusinessService businessService;


    @PostMapping(BUSINESS)
    public BaseResponse save(@RequestBody BusinessRequestDto business)  {
        Business save = businessService.save(business);
        return new BaseResponse(true, save, MESSAGE_SAVE);
    }

    @GetMapping(BY_BUSINESS_ID)
    public BaseResponse getById(@PathVariable Long id){
        Business business = businessService.getById(id);
        return new BaseResponse(true, business, MESSAGE_BY);
    }

    @GetMapping(ALL_BUSINESS)
    public BaseResponse findAll(){
        List<Business> businesses = businessService.findAll();
        return new BaseResponse(true, businesses, MESSAGE_LIST);
    }

    @PutMapping(BY_BUSINESS_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody BusinessRequestDto business) {
        Business update = businessService.updateById(id, business);
        return new BaseResponse(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_BUSINESS_ID)
    public BaseResponse deleteById(@PathVariable Long id){
         businessService.deleteById(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
