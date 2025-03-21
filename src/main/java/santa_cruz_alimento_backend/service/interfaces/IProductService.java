package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IProductService {

    ///
    Product createProductos(ProductoRequestDTO productoRequestDTO) throws ExceptionNotFoundException;

    List<ProductoResponseDTO> findAllProduct() throws ExceptionNotFoundException;

    List<ProductoResponseDTO> getProduct() throws ExceptionNotFoundException;

    Product getByProductById(Long productId) throws ExceptionNotFoundException;

   ProductoResponseDTO getProductById(Long productId) throws ExceptionNotFoundException;

    Product updateProduct(Long productId, ProductoRequestDTO productDto) throws ExceptionNotFoundException;

    boolean deleteByProductId(Long id) throws ExceptionNotFoundException;
}
