package santa_cruz_alimento_backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.product.ProductoRequestDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.service.interfaces.IProductService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping(PRODUCT)
    public BaseResponse createproducts(@ModelAttribute ProductoRequestDTO dto) {
        Product product = productService.createProductos(dto);
        return new BaseResponse(true, product, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(GET_PRODUCT)
    public BaseResponse findProduct() {
        var product = productService.getAllProduct();
        return new BaseResponse(true, product, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(ALL_PRODUCT)
    public BaseResponse findAllProduct() {
        var products = productService.findAllProduct();
        return new BaseResponse(true, products, ReplyMessage.MESSAGE_LIST);
    }

    @GetMapping(GET_BY_PRODUCT_ID)
    public BaseResponse getById(@PathVariable Long id) {
        var productDtoId = productService.getByProductById(id);
        return new BaseResponse(true, productDtoId, ReplyMessage.MESSAGE_BY);
    }

    @GetMapping(BY_PRODUCTS_ID)
    public BaseResponse getByProductId(@PathVariable Long id) {
        var productDtoId = productService.getProductById(id);
        return new BaseResponse(true, productDtoId, ReplyMessage.MESSAGE_BY);
    }

    @PutMapping(GET_BY_PRODUCT_ID)
    public BaseResponse updateById(@PathVariable Long id, @ModelAttribute ProductoRequestDTO productDto){
        Product update = productService.updateProduct(id, productDto);
        return new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);
    }

    @DeleteMapping(GET_BY_PRODUCT_ID)
    public BaseResponse deleteById(@PathVariable Long id) {
        boolean success = productService.deleteByProductId(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }
}
