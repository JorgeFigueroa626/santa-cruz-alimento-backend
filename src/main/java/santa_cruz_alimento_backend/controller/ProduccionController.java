package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.ProduccionRequestDto;
import santa_cruz_alimento_backend.dto.response.ProduccionResponseDTO;
import santa_cruz_alimento_backend.entity.model.Produccion;
import santa_cruz_alimento_backend.service.interfaces.IProduccionService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class ProduccionController {

    @Autowired
    private IProduccionService produccionService;

    @GetMapping(PRODUCCION_BY_PRODUCTO_ID)
    public BaseResponse calcular(@PathVariable Long productoId, @RequestParam double produccion)  {
        Produccion calcular = produccionService.calcularProduccion(productoId, produccion);
        return new BaseResponse(true, calcular, MESSAGE_BY);
    }

    @PostMapping(PRODUCCION_BY_PRODUCTO_ID)
    public BaseResponse registrarProduccion(@PathVariable Long productoId, @RequestParam double solicitudProduccion){
        Produccion nuevaProduccion = produccionService.registrarProduccion(productoId, solicitudProduccion);
        return new BaseResponse(true, nuevaProduccion, MESSAGE_SAVE);
    }

    @GetMapping(PRODUCCION)
    public BaseResponse findAll() {
        List<ProduccionResponseDTO> list =produccionService.findAllProduccions();
        return new BaseResponse(true, list, MESSAGE_LIST);
    }

    @GetMapping(BY_PRODUCCIONS_ID)
    public BaseResponse getByProduccionId(@PathVariable Long id){
        ProduccionResponseDTO dto = produccionService.getByProduccionId(id);
        return new BaseResponse(true, dto, MESSAGE_BY);
    }

    @PutMapping(BY_PRODUCCION_ID)
    public BaseResponse editar(@PathVariable Long id, @RequestBody ProduccionRequestDto produccion){
        Produccion  editar = produccionService.editarProduccionById(id, produccion);
        return new BaseResponse(true, editar, MESSAGE_UPDATE);
    }

}
