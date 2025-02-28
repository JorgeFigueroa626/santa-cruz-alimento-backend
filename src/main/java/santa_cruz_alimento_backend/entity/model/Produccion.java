package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.dto.Response.ProduccionResponseDTO;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "produccions")
public class Produccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double solicitud_produccion;

    private Integer producido;

    private String comentario;

    @Column(name = "fecha_produccion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp fechaProduccion;


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnore  // Evita la serialización infinita
    private Product producto;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marca este lado como el lado "dueño" de la relación
    private List<ProduccionIngrediente> ingredientes = new ArrayList<>();

    public ProduccionResponseDTO produccionDTO(){
        ProduccionResponseDTO dto = new ProduccionResponseDTO();
        dto.setId(id);
        dto.setSolicitud_proudcion(solicitud_produccion);
        dto.setProducido(producido);
        dto.setComentario(comentario);
        dto.setFecha_produccion(fechaProduccion);
        dto.setProductoId(producto.getId());
        dto.setProducto_name(producto.getName());
        return  dto;
    }


}
