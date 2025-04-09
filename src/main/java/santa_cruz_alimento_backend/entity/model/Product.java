package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.dto.response.product.ProductoResponseDTO;

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

    @Column(length = 2)
    private String tamaño;

    private String description;

    private Double price;

    private Integer stock;

    private Integer status;

    /*@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore*/

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "id", nullable = false)
    private Business business;

    @ManyToOne
    @JoinColumn(name = "receta_id", referencedColumnName = "id", nullable = false)
    private Receta receta; // ✅ Un Producto tiene UNA Receta

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // ✅ Evita la serialización infinita
    private List<DetalleVenta> detallesVentas = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", tamaño='" + tamaño + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", category=" + category +
                ", business=" + business +
                ", receta=" + receta +
                '}';
    }

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
        productDto.setCategory_name(category.getName());
        /*productDto.setSubproductId(subProduct.getId());
        productDto.setSubproduct_name(subProduct.getName());*/
        productDto.setBusinessId(business.getId());
        productDto.setBusiness_name(business.getName());
        productDto.setRecetaId(receta.getId());
        productDto.setReceta_name(receta.getName());
        return productDto;
    }
}
