package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.ProductDto;
import santa_cruz_alimento_backend.dto.Request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.Response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
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
    public Product createProducto(ProductoRequestDTO dto) throws ExceptionNotFoundException {
        try {
            Product producto = new Product();
            producto.setName(dto.getName());
            producto.setDescription(dto.getDescription());
            producto.setPrice(dto.getPrice());
            producto.setStock(dto.getStock());

            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Categoria no encontrada"));
            producto.setCategory(category);

            Business business = businessRepository.findById(dto.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Negocio no encontrada"));
            producto.setBusiness(business);

            Receta receta = recetaRepository.findById(dto.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada"));
            producto.setReceta(receta);

            return productRepository.save(producto);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
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
    public List<ProductoResponseDTO> findAllProduct() throws ExceptionNotFoundException {
        try {
            return productRepository.findAll().stream().map(Product::productoDto).collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProductDto> findAllProductByName(String name) {
        return List.of();
    }

    @Override
    public Product getByProductById(Long id) throws ExceptionNotFoundException {
        try {
            return productRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Producto no encontrado con id: " + id));
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Long productId, ProductoRequestDTO productDto) throws ExceptionNotFoundException, Exception {
        try {

            Product optionalProduct = productRepository.findById(productId).orElseThrow(() -> new ExceptionNotFoundException("Producto con ID " + productId + " no encontrado"));
            Category optionalCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ExceptionNotFoundException("Categoria con ID " + productDto.getCategoryId() + " no encontrado"));
            Business optionalBusiness = businessRepository.findById(productDto.getBusinessId()).orElseThrow(() -> new ExceptionNotFoundException("Negocio con ID " + productDto.getBusinessId() + " no encontrado"));
            Receta optionalReceta = recetaRepository.findById(productDto.getRecetaId()).orElseThrow(() -> new ExceptionNotFoundException("Receta con ID " + productDto.getRecetaId() + " no encontrado"));

            optionalProduct.setName(productDto.getName());
            optionalProduct.setDescription(productDto.getDescription());
            optionalProduct.setPrice(productDto.getPrice());
            optionalProduct.setStock(productDto.getStock());
            optionalProduct.setCategory(optionalCategory);
            optionalProduct.setBusiness(optionalBusiness);
            optionalProduct.setReceta(optionalReceta);

            return productRepository.save(optionalProduct);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean deleteByProductId(Long id) throws ExceptionNotFoundException{
        try {
            Product productId = productRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Producto no encontrado con id: " + id));
            productRepository.deleteById(productId.getId());
            return true;
        }catch (ExceptionNotFoundException e){
            return false;
        }
    }
}
