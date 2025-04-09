package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.purchase.CompraRequestDto;
import santa_cruz_alimento_backend.dto.response.purchase.CompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.service.interfaces.ICompraService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;


import java.util.List;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @PostMapping(COMPRA)
    public BaseResponse crearCompra(@RequestBody CompraRequestDto compraRequestDto)  {
        Compra save = compraService.createCompra(compraRequestDto);
        return new BaseResponse(true, save, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(ALL_COMPRA)
    public BaseResponse getAllCompras() {
        List<CompraResponseDto> compras = compraService.findAll();
        return new BaseResponse(true, compras, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(BY_COMPRA_ID)
    public BaseResponse getCompraById(@PathVariable Long id) {
        CompraResponseDto compra = compraService.getByCompraId(id);
        return new BaseResponse(true, compra, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(COMPRAS_BY_INGREDIENTE_ID)
    public BaseResponse obtenerComprasPorIngrediente(@PathVariable Long ingredienteId)  {
        List<Compra> compras = compraService.obtenerComprasPorIngrediente(ingredienteId);
        if (compras.isEmpty()) {
            return new BaseResponse(false, HttpStatus.BAD_REQUEST, "No hay compras"); // No hay compras
        }
        return new BaseResponse(true, compras, ReplyMessage.MESSAGE_LIST); // Retorna las compras encontradas
    }

    @DeleteMapping(BY_COMPRA_ID)
    public BaseResponse deleteById(@PathVariable Long id) {
        compraService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
