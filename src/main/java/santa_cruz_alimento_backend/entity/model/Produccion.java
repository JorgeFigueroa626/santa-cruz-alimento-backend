package santa_cruz_alimento_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import santa_cruz_alimento_backend.util.constant.ReplyProduction;


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

    private Double producido;

    private String comentario;

    @Column(name = "fecha_produccion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/La_Paz")
    private Timestamp fechaProduccion;

    @Enumerated(EnumType.STRING)
    private ReplyProduction status;

    private String nombre_sub_producto;
    private Integer cantidad_sub_producto;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnore  // Evita la serialización infinita
    private Product producto;

    @OneToMany(mappedBy = "produccion", cascade = CascadeType.ALL)
    @JsonManagedReference // Marca este lado como el lado "dueño" de la relación
    private List<DetalleProduccion> detalleProduccions = new ArrayList<>();

}
