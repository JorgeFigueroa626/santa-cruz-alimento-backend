package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.service.interfaces.IBusinessService;

import java.util.List;

import static santa_cruz_alimento_backend.Constante.Constante.*;


@RestController
@RequestMapping(API)
public class BusinessController {

    @Autowired
    private IBusinessService businessService;


    @PostMapping(BUSINESS)
    public ResponseEntity<?> save(@RequestBody Business business){
        try {
            return ResponseEntity.ok(businessService.save(business));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(BY_BUSINESS_ID)
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(businessService.getById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(ALL_BUSINESS)
    public ResponseEntity<List<?>> findAll(){
        try {
            return ResponseEntity.ok(businessService.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
