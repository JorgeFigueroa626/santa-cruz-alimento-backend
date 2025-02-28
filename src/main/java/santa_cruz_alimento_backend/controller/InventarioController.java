package santa_cruz_alimento_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import santa_cruz_alimento_backend.util.shared.JsonResult;

import static santa_cruz_alimento_backend.constante.Constante.*;

import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

/*
@RestController
@RequestMapping(API)
public class InventarioController {

    @Autowired
    private IInventarioService inventarioService;

    // Consultar el inventario de un ingrediente específico
    @GetMapping(INVENTARIO_INGREDIENTE_BY_ID)
    public JsonResult obtenerInventarioPorIngrediente(@PathVariable Long id) throws ExceptionNotFoundException {
        Inventario inventario = inventarioService.getInventarioByIngrediente(id);
        return new JsonResult(true, inventario, MESSAGE_LIST);
    }
}
*/
