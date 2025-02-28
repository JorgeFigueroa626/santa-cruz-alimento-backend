package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;


@RestController
@RequestMapping(API)
public class BusinessController {

    @Autowired
    private IBusinessService businessService;


    @PostMapping(BUSINESS)
    public JsonResult save(@RequestBody Business business) throws ExceptionNotFoundException {
        Business save = businessService.save(business);
        return new JsonResult(true, save, MESSAGE_SAVE);
    }

    @GetMapping(BY_BUSINESS_ID)
    public JsonResult getById(@PathVariable Long id) throws ExceptionNotFoundException{
        Business business = businessService.getById(id);
        return new JsonResult(true, business, MESSAGE_BY);
    }

    @GetMapping(ALL_BUSINESS)
    public JsonResult findAll() throws ExceptionNotFoundException{
        List<Business> businesses = businessService.findAll();
        return new JsonResult(true, businesses, MESSAGE_LIST);
    }

    @PutMapping(BY_BUSINESS_ID)
    public JsonResult updateById(@PathVariable Long id, @RequestBody Business business) throws ExceptionNotFoundException{
        Business update =businessService.updateById(id, business);
        return new JsonResult(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_BUSINESS_ID)
    public JsonResult deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
         businessService.deleteById(id);
        return new JsonResult(true, null, MESSAGE_DELETE);
    }
}
