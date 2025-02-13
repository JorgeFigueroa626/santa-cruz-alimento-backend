package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.ProductDto;

import java.io.IOException;
import java.util.List;

@Service
public interface IProductService {

    public boolean addProduct(ProductDto productDto) throws IOException;

    public List<ProductDto> findAllProduct();

    public List<ProductDto> findAllProductByName(String name);

    /*
    public List<ProductDto> getAllProducts();
    public List<ProductDto> getAllProductsByTitle(String name);

     */

    //public ProductDetailDto getProductDetailById(Long productId);

    public boolean deleteByProductId(Long id);

    public ProductDto getByProductById(Long productId);

    public boolean updateProduct(Long productId, ProductDto productDto) throws IOException;
}
