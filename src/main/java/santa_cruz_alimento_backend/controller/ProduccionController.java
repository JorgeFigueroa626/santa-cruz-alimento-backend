package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.production.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.request.production.ProductionRequestDto;
import santa_cruz_alimento_backend.dto.response.production.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class ProduccionController {

    @Autowired
    private IProduccionService produccionService;

    @GetMapping(PRODUCCION)
    public BaseResponse findAll() {
        var list =produccionService.findAllProduccions();
        return new BaseResponse(true, list, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(PRODUCCION_BY_ID)
    public BaseResponse getByProductionId(@PathVariable Long id){
        var dto = produccionService.getByProduccionId(id);
        return new BaseResponse(true, dto, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(CANCULAR_PRODUCCION)
    public BaseResponse calcular(@RequestBody ProductionRequestDto requestDto)  {
        var calcular = produccionService.calcularProduccion(requestDto);
        return new BaseResponse(true, calcular, ReplyMessage.MESSAGE_BY);
    }

    @PostMapping(PRODUCCION)
    public BaseResponse solicitProduction(@RequestBody ProductionRequestDto requestDto){
        var nuevaProduccion = produccionService.solicitarProduccion(requestDto);
        return new BaseResponse(true, nuevaProduccion, ReplyMessage.MESSAGE_SAVE);
    }

    @PutMapping(PRODUCCION_BY_ID)
    public BaseResponse confirmProduction(@PathVariable Long id, @RequestBody ProduccionRequestDto produccion){
        var  editar = produccionService.confirmProductionById(id, produccion);
        return new BaseResponse(true, editar, ReplyMessage.MESSAGE_UPDATE);
    }

    @PutMapping(CANCEL_PRODUCCION_BY_ID)
    public BaseResponse cancelProductionById(@PathVariable Long id){
        var response = produccionService.cancelProductionById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_PRODUCTION_CANCEL);
    }

}
