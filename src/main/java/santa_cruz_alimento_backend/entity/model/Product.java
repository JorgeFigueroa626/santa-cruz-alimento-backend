package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import santa_cruz_alimento_backend.dto.response.ProductoResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String description;

    private Double price;

    private Integer stock;

    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Business business;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receta_id", referencedColumnName = "id", nullable = false)
    private Receta receta; // ✅ Un Producto tiene UNA Receta

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Evita la serialización infinita
    private List<DetalleVenta> detallesVentas = new ArrayList<>();

    public ProductoResponseDTO productoDto(){
        ProductoResponseDTO productDto = new ProductoResponseDTO();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setImage_url(image);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setStock(stock);
        productDto.setStatus(status);
        productDto.setCategoryId(category.getId());
        productDto.setBusinessId(business.getId());
        productDto.setBusiness_name(business.getName());
        productDto.setRecetaId(receta.getId());
        productDto.setReceta_name(receta.getName());
        return productDto;
    }
}
