package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.ProductoRequestDTO;
import santa_cruz_alimento_backend.dto.response.*;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.*;
import santa_cruz_alimento_backend.service.interfaces.IFirebaseStorageService;
import santa_cruz_alimento_backend.service.interfaces.IProductService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.container.Container;
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

    /*@Autowired
    private ISubProductRepository subProductRepository;*/

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product createProductos(ProductoRequestDTO requestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDTO);

            Product producto = new Product();
            producto.setName(requestDTO.getName());

            String imageUrl = storageService.uploadImage(Container.PRODUCTS, requestDTO.getImage());
            producto.setImage(imageUrl);

            producto.setDescription(requestDTO.getDescription());
            producto.setPrice(requestDTO.getPrice());
            producto.setStock(0);
            producto.setStatus(ReplyStatus.ACTIVE.getValue());

            if (requestDTO.getCategoryId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "categoria");

            Category category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_CATEGORY_WITH_ID + requestDTO.getCategoryId()));
            producto.setCategory(category);

            if (requestDTO.getBusinessId() == null) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "negocio");
            }

            Business business = businessRepository.findById(requestDTO.getBusinessId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BUSINESS_WITH_ID + requestDTO.getBusinessId()));
            producto.setBusiness(business);

            /*if (requestDTO.getSubproductId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "sub product");

            SubProduct subProduct = subProductRepository.findById(requestDTO.getSubproductId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Sub Producto no encontrada con el Id : " + requestDTO.getSubproductId()));
            producto.setSubProduct(subProduct);*/

            if (requestDTO.getRecetaId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "receta");

            Receta receta = recetaRepository.findById(requestDTO.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + requestDTO.getRecetaId()));

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

            return products.stream().map(product -> {
                ProductoResponseDTO responseDTO = new ProductoResponseDTO();
                responseDTO.setId(product.getId());
                responseDTO.setName(product.getName());
                responseDTO.setImage_url(product.getImage());
                responseDTO.setDescription(product.getDescription());
                responseDTO.setPrice(product.getPrice());
                responseDTO.setStock(product.getStock());
                responseDTO.setStatus(product.getStatus());

                responseDTO.setCategoryId(product.getCategory().getId());
                responseDTO.setCategory_name(product.getCategory().getName());

                /*SubProduct category = subProductRepository.findById(product.getSubProduct().getId()).orElseThrow(() -> new ExceptionNotFoundException("Categoria no encontrado con id: " + product.getCategory().getId()));
                List<DetalleBaseResponseDto> responseDtoList = category.getDetalleBase().stream().map(iDto->{
                    DetalleBaseResponseDto dto = new DetalleBaseResponseDto();
                    dto.setId(iDto.getId());
                    dto.setIngredienteId(iDto.getIngrediente().getId());
                    dto.setNombre_ingrediente(iDto.getIngrediente().getName());
                    dto.setCantidad(iDto.getCantidad());
                    dto.setUnidad(iDto.getUnidad());
                    return dto;
                }).collect(Collectors.toList());

                responseDTO.setSubproductId(product.getSubProduct().getId());
                responseDTO.setSubproduct_name(product.getSubProduct().getName());
                responseDTO.setDetallesBases(responseDtoList);*/

                responseDTO.setBusinessId(product.getBusiness().getId());
                responseDTO.setBusiness_name(product.getBusiness().getName());

                Receta receta = recetaRepository.findById(product.getReceta().getId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + product.getReceta().getId()));
                // Convertir detalles de recera a DTO
                List<DetalleRecetaResponseDto> detallesDto = receta.getDetalleRecetas().stream().map(detalle -> {
                    DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
                    detalleDto.setId(detalle.getId());
                    detalleDto.setIngredienteId(detalle.getIngrediente().getId());
                    detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setUnidad(detalle.getUnidad());
                    return detalleDto;
                }).collect(Collectors.toList());

                responseDTO.setRecetaId(receta.getId());
                responseDTO.setReceta_name(receta.getName());
                responseDTO.setDetalleReceta(detallesDto);

                return responseDTO;
            }).collect(Collectors.toList());

        }catch (Exception e){
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ProductoResponseDTO> getProduct() throws ExceptionNotFoundException {
        try {
            return productRepository.findAll().stream().map(Product::productoDto).collect(Collectors.toList());
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

            ProductoResponseDTO responseDTO = new ProductoResponseDTO();
            responseDTO.setId(product.getId());
            responseDTO.setName(product.getName());
            responseDTO.setImage_url(product.getImage());
            responseDTO.setDescription(product.getDescription());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setStock(product.getStock());
            responseDTO.setStatus(product.getStatus());

            responseDTO.setCategoryId(product.getCategory().getId());
            responseDTO.setCategory_name(product.getCategory().getName());

            /*SubProduct category = subProductRepository.findById(product.getSubProduct().getId()).orElseThrow(() -> new ExceptionNotFoundException("Categoria no encontrado con id: " + product.getCategory().getId()));
            List<DetalleBaseResponseDto> responseDtoList = category.getDetalleBase().stream().map(iDto->{
                DetalleBaseResponseDto dto = new DetalleBaseResponseDto();
                dto.setId(iDto.getId());
                dto.setIngredienteId(iDto.getIngrediente().getId());
                dto.setNombre_ingrediente(iDto.getIngrediente().getName());
                dto.setCantidad(iDto.getCantidad());
                dto.setUnidad(iDto.getUnidad());
                return dto;
            }).collect(Collectors.toList());

            responseDTO.setSubproductId(product.getSubProduct().getId());
            responseDTO.setSubproduct_name(product.getSubProduct().getName());
            responseDTO.setDetallesBases(responseDtoList);*/

            responseDTO.setBusinessId(product.getBusiness().getId());
            responseDTO.setBusiness_name(product.getBusiness().getName());

            Receta receta = recetaRepository.findById(product.getReceta().getId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + product.getReceta().getId()));
            // Convertir detalles de recera a DTO
            List<DetalleRecetaResponseDto> detallesDto = receta.getDetalleRecetas().stream().map(detalle -> {
                DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
                detalleDto.setId(detalle.getId());
                detalleDto.setIngredienteId(detalle.getIngrediente().getId());
                detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setUnidad(detalle.getUnidad());
                return detalleDto;
            }).collect(Collectors.toList());

            responseDTO.setRecetaId(receta.getId());
            responseDTO.setReceta_name(receta.getName());
            responseDTO.setDetalleReceta(detallesDto);

            return responseDTO;


        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

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

            /*if (productDto.getSubproductId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "sub product");
            SubProduct subProduct = subProductRepository.findById(productDto.getSubproductId())
                    .orElseThrow(() -> new ExceptionNotFoundException("Sub Producto no encontrada con el Id : " + productDto.getSubproductId()));*/

            if (productDto.getRecetaId() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "receta");
            Receta optionalReceta = recetaRepository.findById(productDto.getRecetaId())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + productDto.getRecetaId()));

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
            //optionalProduct.setSubProduct(subProduct);
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
}
