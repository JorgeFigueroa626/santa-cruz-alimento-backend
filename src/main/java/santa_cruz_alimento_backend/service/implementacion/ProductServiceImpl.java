package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.response.ProductoResponseDTO;
import santa_cruz_alimento_backend.entity.model.Business;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IBusinessRepository;
import santa_cruz_alimento_backend.repository.ICategoryRepository;
import santa_cruz_alimento_backend.repository.IProductRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IFirebaseStorageService;
import santa_cruz_alimento_backend.service.interfaces.IProductService;
import santa_cruz_alimento_backend.util.enums.ReplyStatu;
import santa_cruz_alimento_backend.util.shared.Container;

import java.util.List;
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

    @Autowired
    private IFirebaseStorageService storageService;


    @Override
    public Product createProducto(ProductoRequestDTO productoRequestDTO) throws ExceptionNotFoundException {
        try {
            Product producto = new Product();
            producto.setName(productoRequestDTO.getName());
            producto.setDescription(productoRequestDTO.getDescription());
            producto.setPrice(productoRequestDTO.getPrice());
            producto.setStock(0);
            producto.setStatus(ReplyStatu.ACTIVE.getValue());

            Category category = categoryRepository.findById(productoRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Categoria no encontrada con el Id : " + productoRequestDTO.getCategoryId()));
            producto.setCategory(category);

            Business business = businessRepository.findById(productoRequestDTO.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Negocio no encontrada con el Id : " + productoRequestDTO.getBusinessId()));
            producto.setBusiness(business);

            Receta receta = recetaRepository.findById(productoRequestDTO.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con el Id: " + productoRequestDTO.getRecetaId()));
            producto.setReceta(receta);

            return productRepository.save(producto);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Product createProductos(ProductoRequestDTO productoRequestDTO) throws ExceptionNotFoundException {
        try {
            Product producto = new Product();
            producto.setName(productoRequestDTO.getName());

            String imageUrl = storageService.uploadImage(Container.PRODUCTS, productoRequestDTO.getImage());
            producto.setImage(imageUrl);

            producto.setDescription(productoRequestDTO.getDescription());
            producto.setPrice(productoRequestDTO.getPrice());
            producto.setStock(0);
            producto.setStatus(ReplyStatu.ACTIVE.getValue());

            Category category = categoryRepository.findById(productoRequestDTO.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Categoria no encontrada con el Id : " + productoRequestDTO.getCategoryId()));
            producto.setCategory(category);

            Business business = businessRepository.findById(productoRequestDTO.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Negocio no encontrada con el Id : " + productoRequestDTO.getBusinessId()));
            producto.setBusiness(business);

            Receta receta = recetaRepository.findById(productoRequestDTO.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con el Id: " + productoRequestDTO.getRecetaId()));
            producto.setReceta(receta);

            return productRepository.save(producto);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
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
    public Product getByProductById(Long id) throws ExceptionNotFoundException {
        try {
            return productRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Producto no encontrado con id: " + id));
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Long productId, ProductoRequestDTO productDto) throws ExceptionNotFoundException {
        try {

            Product optionalProduct = productRepository.findById(productId).orElseThrow(() -> new ExceptionNotFoundException("Producto con ID " + productId + " no encontrado"));
            Category optionalCategory = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ExceptionNotFoundException("Categoria con ID " + productDto.getCategoryId() + " no encontrado"));
            Business optionalBusiness = businessRepository.findById(productDto.getBusinessId()).orElseThrow(() -> new ExceptionNotFoundException("Negocio con ID " + productDto.getBusinessId() + " no encontrado"));
            Receta optionalReceta = recetaRepository.findById(productDto.getRecetaId()).orElseThrow(() -> new ExceptionNotFoundException("Receta con ID " + productDto.getRecetaId() + " no encontrado"));

            optionalProduct.setName(productDto.getName());

            // Verificar si hay una nueva imagen
            if (productDto.getImage() != null && !productDto.getImage().isEmpty()) {
                String image_url = storageService.uploadImage(Container.PRODUCTS, productDto.getImage());
                optionalProduct.setImage(image_url);
            }

            optionalProduct.setDescription(productDto.getDescription());
            optionalProduct.setPrice(productDto.getPrice());
            optionalProduct.setStock(productDto.getStock());
            optionalProduct.setStatus(productDto.getStatus());
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
            productId.setStatus(ReplyStatu.INACTIVE.getValue());
            productRepository.save(productId);
            return true;
        }catch (ExceptionNotFoundException e){
            return false;
        }
    }
}
