package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.production.SubProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.SubProductionRequestDto;
import santa_cruz_alimento_backend.service.interfaces.ISubProduccionService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class SubProduccionController {
    
    @Autowired
    private ISubProduccionService subProduccionService;

    @GetMapping(SUB_PRODUCCION)
    public BaseResponse findAll() {
        var list =subProduccionService.findAllSubProduccions();
        return new BaseResponse(true, list, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(SUB_PRODUCCION_BY_ID)
    public BaseResponse getByProductionId(@PathVariable Long id){
        var dto = subProduccionService.getBySubProduccionId(id);
        return new BaseResponse(true, dto, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(CANCULAR_SUB_PRODUCCION)
    public BaseResponse calcular(@RequestBody SubProductionRequestDto requestDto)  {
        var calcular = subProduccionService.calcularSubProduction(requestDto);
        return new BaseResponse(true, calcular, ReplyMessage.MESSAGE_BY);
    }

    @PostMapping(SUB_PRODUCCION)
    public BaseResponse solicitProduction(@RequestBody SubProductionRequestDto requestDto){
        var nuevaProduccion = subProduccionService.solicitarSubProduction(requestDto);
        return new BaseResponse(true, nuevaProduccion, ReplyMessage.MESSAGE_SAVE);
    }

    @PutMapping(SUB_PRODUCCION_BY_ID)
    public BaseResponse confirmProduction(@PathVariable Long id, @RequestBody SubProduccionRequestDto produccion){
        var  editar = subProduccionService.confirmSubProductionById(id, produccion);
        return new BaseResponse(true, editar, ReplyMessage.MESSAGE_UPDATE);
    }

    @PutMapping(CANCEL_SUB_PRODUCCION_BY_ID)
    public BaseResponse cancelProductionById(@PathVariable Long id){
        var response = subProduccionService.cancelProductionById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_PRODUCTION_CANCEL);
    }
}
