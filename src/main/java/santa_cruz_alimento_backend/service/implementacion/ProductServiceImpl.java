package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.ProductDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.service.interfaces.IProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IBusinessRepository businessRepository;


    @Override
    public boolean addProduct(ProductDto productDto) throws IOException {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());

            Category categoryId = categoryRepository.findById(productDto.getCategory_id()).orElseThrow(() -> new RuntimeException("Category no encontrado con id: " + productDto.getCategory_id()));
            Business businessId = businessRepository.findById(productDto.getBusiness_id()).orElseThrow(() -> new RuntimeException("Negocio no encontrado con id: " + productDto.getBusiness_id()));

            product.setCategory(categoryId);
            product.setBusiness(businessId);
            productRepository.save(product);
            return true;

        }catch (Exception e ){
            return false;
        }
    }

    @Override
    public List<ProductDto> findAllProduct() {
        return productRepository.findAll().stream().map(Product::productDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> findAllProductByName(String name) {
        return List.of();
    }

    @Override
    public boolean deleteByProductId(Long id) {
        try {
            Product productId = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
            productRepository.deleteById(productId.getId());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public ProductDto getByProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().productDto();
        }
        return null;
    }

    @Override
    public boolean updateProduct(Long productId, ProductDto productDto) throws IOException {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategory_id());
            Optional<Business> optionalBusiness = businessRepository.findById(productDto.getCategory_id());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setName(productDto.getName());
                product.setDescription(productDto.getDescription());
                product.setPrice(productDto.getPrice());
                product.setCategory(optionalCategory.get());
                product.setBusiness(optionalBusiness.get());
                productRepository.save(product);
                return true;
            }else {
                return false;
            }
    }
}
