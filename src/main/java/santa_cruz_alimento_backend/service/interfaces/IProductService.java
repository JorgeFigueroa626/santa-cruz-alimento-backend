package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.ProductDto;
import santa_cruz_alimento_backend.dto.Request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.Response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public interface IProductService {

    public boolean addProduct(ProductDto productDto) throws IOException;
    public List<ProductDto> findAllProductByName(String name);

    /*
    public List<ProductDto> getAllProducts();
    public List<ProductDto> getAllProductsByTitle(String name);

     */
    //public ProductDetailDto getProductDetailById(Long productId);

    ///
    Product createProducto(ProductoRequestDTO productoRequestDTO) throws ExceptionNotFoundException;

    public List<ProductoResponseDTO> findAllProduct() throws ExceptionNotFoundException;

    Product getByProductById(Long productId) throws ExceptionNotFoundException;

    Product updateProduct(Long productId, ProductoRequestDTO productDto) throws ExceptionNotFoundException, Exception;

    public boolean deleteByProductId(Long id) throws ExceptionNotFoundException;
}
