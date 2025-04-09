package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.product.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.response.product.ProductoResponseDTO;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IBaseMapper;
import santa_cruz_alimento_backend.repository.*;
import santa_cruz_alimento_backend.service.interfaces.IFirebaseStorageService;
import santa_cruz_alimento_backend.service.interfaces.IProductService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.constant.Container;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

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

    @Autowired
    private IBaseMapper baseMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Transactional
    @Override
    public Product createProductos(ProductoRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDTO);

            Product producto = new Product();
            producto.setName(requestDTO.getName());
            producto.setTamaño(requestDTO.getTamaño());

            String imageUrl = storageService.uploadImage(Container.PRODUCTS, requestDTO.getImage());
            producto.setImage(imageUrl);

            producto.setTamaño(requestDTO.getTamaño());
            producto.setDescription(requestDTO.getDescription());
            producto.setPrice(requestDTO.getPrice());
            producto.setStock(0);
            producto.setStatus(ReplyStatus.ACTIVE.getValue());

            if (requestDTO.getCategoryId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "categoria");
            Category category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + requestDTO.getCategoryId()));
            if (category.getStatus()==0)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_SUB_PRODUCT_STATUS + category.getName());
            producto.setCategory(category);

            if (requestDTO.getBusinessId() == null) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "negocio");
            }
            Business business = businessRepository.findById(requestDTO.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + requestDTO.getBusinessId()));
            if (business.getStatus()==0)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_BUSINESS_STATUS + business.getName());
            producto.setBusiness(business);


            if (requestDTO.getRecetaId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "receta");
            Receta receta = recetaRepository.findById(requestDTO.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + requestDTO.getRecetaId()));
            if (receta.getStatus() ==0)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_NOT_RECIPE_STATUS + receta.getName());
            producto.setReceta(receta);

            Product save =  productRepository.save(producto);

            logger.info("Producto registrado : {}", save);
            return save;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProductoResponseDTO> findAllProduct() throws ExceptionNotFoundException {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return products.stream().map(this::convertproductoResponseDTO).collect(Collectors.toList());

        }catch (Exception e){
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProductoResponseDTO> getAllProduct() throws ExceptionNotFoundException {
        try {
            return productRepository.findAll().stream().map(this::convertproductoResponseDTO).collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Product getByProductById(Long id) throws ExceptionNotFoundException {
        try {
            return productRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public ProductoResponseDTO getProductById(Long productId) throws ExceptionNotFoundException {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + productId));

            ProductoResponseDTO responseDTO = convertproductoResponseDTO(product);

            return responseDTO;


        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Long productId, ProductoRequestDTO productDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de modificar: {}", productDto);

            Product optionalProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + productId));

            Category optionalCategory = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + productDto.getCategoryId()));

            if (productDto.getBusinessId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "negocio");
            Business optionalBusiness = businessRepository.findById(productDto.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + productDto.getBusinessId()));

            if (productDto.getRecetaId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "receta");
            Receta optionalReceta = recetaRepository.findById(productDto.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + productDto.getRecetaId()));

            optionalProduct.setName(productDto.getName());
            optionalProduct.setTamaño(productDto.getTamaño());

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

            Product save = productRepository.save(optionalProduct);

            logger.info("Producto modificado : {}", save);
            return save;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean deleteByProductId(Long id) throws ExceptionNotFoundException{
        try {
            Product productId = productRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_PRODUCT_WITH_ID + id));
            productId.setStatus(ReplyStatus.INACTIVE.getValue());
            productRepository.save(productId);
            logger.info("Producto eliminado : {}", productId);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());

        }
    }

    private ProductoResponseDTO convertproductoResponseDTO(Product product){
        ProductoResponseDTO responseDTO = new ProductoResponseDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setTamaño(product.getTamaño());
        responseDTO.setImage_url(product.getImage());
        responseDTO.setDescription(product.getDescription());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setStock(product.getStock());
        responseDTO.setStatus(product.getStatus());

        responseDTO.setCategoryId(product.getCategory().getId());
        responseDTO.setCategory_name(product.getCategory().getName());

        responseDTO.setBusinessId(product.getBusiness().getId());
        responseDTO.setBusiness_name(product.getBusiness().getName());

        responseDTO.setRecetaId(product.getReceta().getId());
        responseDTO.setReceta_name(product.getReceta().getName());
        responseDTO.setDetalleReceta(convertirDetallesRecetaADto(product.getReceta().getDetalleRecetas()));

        return responseDTO;
    }

    private List<DetalleRecetaResponseDto> convertirDetallesRecetaADto(List<DetalleRecetas> detallesRecetas) {
        return detallesRecetas.stream().map(detalle -> {
            DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setBaseId(detalle.getBase().getId());
            detalleDto.setNombre_base(detalle.getBase().getName());
            detalleDto.setDescription_base(detalle.getBase().getDescription());
            detalleDto.setDetalleBases(baseMapper.toDetailBaseResponseDtoList(detalle.getBase().getDetalleBases()));
            return detalleDto;
        }).collect(Collectors.toList());
    }

}
