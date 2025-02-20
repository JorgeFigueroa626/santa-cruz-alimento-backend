package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.dto.ProductDto;
import santa_cruz_alimento_backend.entity.dto.ProductoDto;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.service.interfaces.IProductService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

@RestController
@RequestMapping(API)
public class ProductController {

    @Autowired
    private IProductService productService;

    //@PostMapping(PRODUCT)
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) throws Exception{
        boolean success = productService.addProduct(productDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(PRODUCTS)
    public ResponseEntity<Product> create(@RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productService.createProducto(dto));
    }

    @GetMapping(ALL_PRODUCT)
    public ResponseEntity<List<?>> findAll(){
        return ResponseEntity.ok(productService.findAllProduct());
    }

    @GetMapping(BY_PRODUCT_ID)
    public ResponseEntity<?> getById(@PathVariable Long id){
        ProductoDto productDtoId = productService.getByProductById(id);
        if (productDtoId != null) {
            return ResponseEntity.ok(productDtoId);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(BY_PRODUCT_ID)
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody ProductoDto productDto){
        try {
            boolean update = productService.updateProduct(id, productDto);
            if (update) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(BY_PRODUCT_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        boolean success = productService.deleteByProductId(id);
        if (success) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
