package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import santa_cruz_alimento_backend.dto.Response.ProductoResponseDTO;

@Entity
@Data
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Integer stock;

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

    public ProductoResponseDTO productoDto(){
        ProductoResponseDTO productDto = new ProductoResponseDTO();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setStock(stock);
        productDto.setCategoryId(category.getId());
        productDto.setBusinessId(business.getId());
        productDto.setBusiness_name(business.getName());
        productDto.setRecetaId(receta.getId());
        productDto.setReceta_name(receta.getName());
        return productDto;
    }
}
