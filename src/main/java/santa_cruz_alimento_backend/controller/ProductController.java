package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IProductService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;
import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping(PRODUCT)
    public BaseResponse create(@RequestBody ProductoRequestDTO dto) throws ExceptionNotFoundException {
        Product product = productService.createProducto(dto);
        return new BaseResponse(true, product, MESSAGE_SAVE);
    }

    @PostMapping(PRODUCTS)
    public BaseResponse createproducts(@ModelAttribute ProductoRequestDTO dto) throws ExceptionNotFoundException {
        Product product = productService.createProductos(dto);
        return new BaseResponse(true, product, MESSAGE_SAVE);
    }

    @GetMapping(ALL_PRODUCT)
    public BaseResponse findAll() throws ExceptionNotFoundException{
        List<ProductoResponseDTO> products = productService.findAllProduct();
        return new BaseResponse(true, products, MESSAGE_LIST);
    }

    @GetMapping(BY_PRODUCT_ID)
    public BaseResponse getById(@PathVariable Long id) throws ExceptionNotFoundException{
        var productDtoId = productService.getByProductById(id);
        return new BaseResponse(true, productDtoId, MESSAGE_BY);
    }

    @PutMapping(BY_PRODUCT_ID)
    public BaseResponse updateById(@PathVariable Long id, @ModelAttribute ProductoRequestDTO productDto){
        Product update = productService.updateProduct(id, productDto);
        return new BaseResponse(true, update, MESSAGE_UPDATE);
    }

    @DeleteMapping(BY_PRODUCT_ID)
    public BaseResponse deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        boolean success = productService.deleteByProductId(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }
}
