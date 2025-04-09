package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.sale.VentaRequesDto;
import santa_cruz_alimento_backend.dto.response.sale.VentaResponseDto;
import santa_cruz_alimento_backend.entity.model.Venta;
import santa_cruz_alimento_backend.service.interfaces.IVentaService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;


import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @GetMapping(BY_VENTA_ID)
    public BaseResponse getByVentaId(@PathVariable Long id){
        VentaResponseDto venta = ventaService.getByVentaId(id);
        return new BaseResponse(true, venta, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(ALL_VENTA)
    public BaseResponse findAllVentas(){
        List<VentaResponseDto> list = ventaService.findAll();
        return new BaseResponse(true, list, ReplyMessage.MESSAGE_LIST);
    }

    @PostMapping(VENTA)
    public BaseResponse registerVenta(@RequestBody VentaRequesDto requesDto){
        Venta venta = ventaService.registrarVenta(requesDto);
        return new BaseResponse(true, venta, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(VENTAS_BY_USUARIO_ID)
    public BaseResponse findAllVentasByUserId(@PathVariable Long userId){
        var ventas = ventaService.findAllVentasByUsuarioId(userId);
        return new BaseResponse(true, ventas, ReplyMessage.MESSAGE_LIST);
    }

}
