package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.Response.ProduccionResponseDTO;
import santa_cruz_alimento_backend.dto.Response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class ProduccionController {

    @Autowired
    private IProduccionService produccionService;

    @GetMapping(PRODUCCION_BY_PRODUCTO_ID)
    public JsonResult calcular(@PathVariable Long productoId, @RequestParam double produccion) throws ExceptionNotFoundException {
        Produccion calcular = produccionService.calcularProduccion(productoId, produccion);
        return new JsonResult(true, calcular, MESSAGE_BY);
    }

    @PostMapping(PRODUCCION_BY_PRODUCTO_ID)
    public JsonResult registrarProduccion(@PathVariable Long productoId, @RequestParam double solicitudProduccion)  throws ExceptionNotFoundException{
        Produccion nuevaProduccion = produccionService.registrarProduccion(productoId, solicitudProduccion);
        return new JsonResult(true, nuevaProduccion, MESSAGE_SAVE);
    }

    @GetMapping(PRODUCCION)
    public JsonResult findAll() throws ExceptionNotFoundException{
        List<ProduccionResponseDTO> list =produccionService.findAllProduccions();
        return new JsonResult(true, list, MESSAGE_LIST);
    }

    @GetMapping(BY_PRODUCCIONS_ID)
    public JsonResult getByProduccionId(@PathVariable Long id)throws ExceptionNotFoundException{
        ProduccionResponseDTO dto = produccionService.getByProduccionId(id);
        return new JsonResult(true, dto, MESSAGE_BY);
    }

    @PutMapping(BY_PRODUCCION_ID)
    public JsonResult editar(@PathVariable Long id, @RequestBody Produccion produccion)throws ExceptionNotFoundException{
        Produccion  editar = produccionService.editarProduccionById(id, produccion);
        return new JsonResult(true, editar, MESSAGE_UPDATE);
    }

}
