package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.CompraRequestDto;
import santa_cruz_alimento_backend.dto.response.CompraResponseDto;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.ICompraService;


import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @PostMapping(COMPRA)
    public BaseResponse crearCompra(@RequestBody CompraRequestDto compraRequestDto) throws ExceptionNotFoundException {
        Compra save = compraService.createCompra(compraRequestDto);
        return new BaseResponse(true, save, MESSAGE_SAVE);
    }

    @GetMapping(ALL_COMPRA)
    public BaseResponse getAllCompras() throws ExceptionNotFoundException{
        List<CompraResponseDto> compras = compraService.findAll();
        return new BaseResponse(true, compras, MESSAGE_LIST);
    }

    @GetMapping(BY_COMPRA_ID)
    public BaseResponse getCompraById(@PathVariable Long id) throws ExceptionNotFoundException{
        CompraResponseDto compra = compraService.getByCompraId(id);
        return new BaseResponse(true, compra, MESSAGE_BY);
    }

    @GetMapping(COMPRAS_BY_INGREDIENTE_ID)
    public BaseResponse obtenerComprasPorIngrediente(@PathVariable Long ingredienteId) throws ExceptionNotFoundException {
        List<Compra> compras = compraService.obtenerComprasPorIngrediente(ingredienteId);
        if (compras.isEmpty()) {
            return new BaseResponse(false, HttpStatus.BAD_REQUEST, "No hay compras"); // No hay compras
        }
        return new BaseResponse(true, compras, MESSAGE_LIST); // Retorna las compras encontradas
    }

    @DeleteMapping(BY_COMPRA_ID)
    public BaseResponse deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        compraService.deleteById(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
