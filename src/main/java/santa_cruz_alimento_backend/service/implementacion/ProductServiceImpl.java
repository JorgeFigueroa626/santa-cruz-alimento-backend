package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.ProductDto;
import santa_cruz_alimento_backend.entity.dto.ProductoDto;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
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

    @Autowired
    private IRecetaRepository recetaRepository;


    @Override
    public Product createProducto(ProductoDto dto) {
        Product producto = new Product();
        producto.setName(dto.getName());
        producto.setDescription(dto.getDescription());
        producto.setPrice(dto.getPrice());
        producto.setProduction(dto.getProduction());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        producto.setCategory(category);

        Business business = businessRepository.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrada"));
        producto.setBusiness(business);

        Receta receta = recetaRepository.findById(dto.getRecetaId())
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        producto.setReceta(receta);

        return productRepository.save(producto);
    }

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
    public List<ProductoDto> findAllProduct() {
        return productRepository.findAll().stream().map(Product::productoDto).collect(Collectors.toList());
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
    public ProductoDto getByProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().productoDto();
        }
        return null;
    }

    @Override
    public boolean updateProduct(Long productId, ProductoDto productDto) throws Exception {
            Product optionalProduct = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto con ID " + productId + " no encontrado"));
            Category optionalCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new Exception("Categoria con ID " + productDto.getCategoryId() + " no encontrado"));
            Business optionalBusiness = businessRepository.findById(productDto.getBusinessId()).orElseThrow(() -> new Exception("Negocio con ID " + productDto.getBusinessId() + " no encontrado"));
            Receta optionalReceta = recetaRepository.findById(productDto.getRecetaId()).orElseThrow(() -> new Exception("Receta con ID " + productDto.getRecetaId() + " no encontrado"));

            optionalProduct.setName(productDto.getName());
            optionalProduct.setDescription(productDto.getDescription());
            optionalProduct.setPrice(productDto.getPrice());
            optionalProduct.setProduction(productDto.getProduction());
            optionalProduct.setCategory(optionalCategory);
            optionalProduct.setBusiness(optionalBusiness);
            optionalProduct.setReceta(optionalReceta);
            productRepository.save(optionalProduct);
            return true;
    }
}
