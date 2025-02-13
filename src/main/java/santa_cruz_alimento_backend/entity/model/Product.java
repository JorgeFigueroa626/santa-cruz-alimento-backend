package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import santa_cruz_alimento_backend.entity.dto.ProductDto;

import java.util.Set;

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

//    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
//    private Set<Receta> receta;

    public ProductDto productDto(){
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setCategory_id(category.getId());
        productDto.setBusiness_id(business.getId());
        return productDto;
    }
}
