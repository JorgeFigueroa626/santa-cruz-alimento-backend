package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.Request.CompraDTO;
import santa_cruz_alimento_backend.entity.model.Compra;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.ICompraService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class CompraController {

    @Autowired
    private ICompraService compraService;

    @PostMapping(COMPRA)
    public JsonResult crearCompra(@RequestBody CompraDTO compraDTO) throws ExceptionNotFoundException {
        Compra save = compraService.createCompra(compraDTO);
        return new JsonResult(true, save, MESSAGE_SAVE);
    }

    @GetMapping(ALL_COMPRA)
    public JsonResult getAllCompras() throws ExceptionNotFoundException{
        List<Compra> compras = compraService.findAll();
        return new JsonResult(true, compras, MESSAGE_LIST);
    }

    @GetMapping(BY_COMPRA_ID)
    public JsonResult getCompraById(@PathVariable Long id) throws ExceptionNotFoundException{
        Compra compra = compraService.getByCompraId(id);
        return new JsonResult(true, compra, MESSAGE_BY);
    }

    @GetMapping(COMPRAS_BY_INGREDIENTE_ID)
    public JsonResult obtenerComprasPorIngrediente(@PathVariable Long ingredienteId) throws ExceptionNotFoundException {
        List<Compra> compras = compraService.obtenerComprasPorIngrediente(ingredienteId);
        if (compras.isEmpty()) {
            return new JsonResult(false, HttpStatus.BAD_REQUEST, "No hay compras"); // No hay compras
        }
        return new JsonResult(true, compras, MESSAGE_LIST); // Retorna las compras encontradas
    }

    @DeleteMapping(BY_COMPRA_ID)
    public JsonResult deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        compraService.deleteById(id);
        return new JsonResult(true, null, MESSAGE_DELETE);
    }
}
