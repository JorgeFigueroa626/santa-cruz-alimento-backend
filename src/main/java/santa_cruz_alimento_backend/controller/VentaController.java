package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.request.VentaRequesDto;
import santa_cruz_alimento_backend.dto.response.VentaResponseDto;
import santa_cruz_alimento_backend.entity.model.Venta;
import santa_cruz_alimento_backend.service.interfaces.IVentaService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @GetMapping(BY_VENTA_ID)
    public JsonResult getByVentaId(@PathVariable Long id){
        VentaResponseDto venta = ventaService.getByVentaId(id);
        return new JsonResult(true, venta, MESSAGE_BY);
    }

    @GetMapping(ALL_VENTA)
    public JsonResult findAllVentas(){
        List<VentaResponseDto> list = ventaService.findAll();
        return new JsonResult(true, list, MESSAGE_LIST);
    }

    @PostMapping(VENTA)
    public JsonResult registerVenta(@RequestBody VentaRequesDto requesDto){
        Venta venta = ventaService.registrarVenta(requesDto);
        return new JsonResult(true, venta, MESSAGE_SAVE);
    }

    /*@GetMapping(VENTAS_BY_USUARIO_ID)
    public JsonResult findAllVentasByUserId(@PathVariable Long userId){
        List<VentaResponseDto> list = ventaService.findAllVentasByUserId(userId);
        return new JsonResult(true, list, MESSAGE_LIST);
    }*/

}
