package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.ProductDto;
import santa_cruz_alimento_backend.dto.Request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.Response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IProductService;
import santa_cruz_alimento_backend.util.shared.JsonResult;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class ProductController {

    @Autowired
    private IProductService productService;

    //@PostMapping(PRODUCT)
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) throws ExceptionNotFoundException, Exception{
        boolean success = productService.addProduct(productDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(PRODUCT)
    public JsonResult create(@RequestBody ProductoRequestDTO dto) throws ExceptionNotFoundException {
        Product product = productService.createProducto(dto);
        return new JsonResult(true, product, MESSAGE_SAVE);
    }

    @GetMapping(ALL_PRODUCT)
    public JsonResult findAll() throws ExceptionNotFoundException{
        List<ProductoResponseDTO> products = productService.findAllProduct();
        return new JsonResult(true, products, MESSAGE_LIST);
    }

    @GetMapping(BY_PRODUCT_ID)
    public JsonResult getById(@PathVariable Long id) throws ExceptionNotFoundException{
        Product productDtoId = productService.getByProductById(id);
        return new JsonResult(true, productDtoId, MESSAGE_BY);
    }

    @PutMapping(BY_PRODUCT_ID)
    public JsonResult updateById(@PathVariable Long id, @RequestBody ProductoRequestDTO productDto) throws ExceptionNotFoundException, Exception{
        Product update = productService.updateProduct(id, productDto);
        return new JsonResult(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_PRODUCT_ID)
    public JsonResult deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        boolean success = productService.deleteByProductId(id);
        return new JsonResult(true, null, MESSAGE_DELETE);
    }
}
