package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.IngredienteDTO;
import santa_cruz_alimento_backend.entity.dto.RecetaDto;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.entity.model.RecetaIngrediente;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements IRecetaService {

    @Autowired
    private IRecetaRepository recetaRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Override
    public boolean addReceta(Receta recetaDto) throws IOException {
        try {
//            Receta receta = new Receta();
//            receta.setName(recetaDto.getName());
//
//            Product product = productRepository.findById(recetaDto.getProducto_id())
//                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + recetaDto.getProducto_id()));
//
//            // Guardar primero la receta sin ingredientes
//            receta = recetaRepository.save(receta);
//
//            List<RecetaIngrediente> ingredientes = new ArrayList<>();
//            for (RecetaIngrediente ingredienteDTO : recetaDto.getIngredientes()) {
//                RecetaIngrediente ingrediente = new RecetaIngrediente();
//                ingrediente.setCantidad(ingredienteDTO.getCantidad());
//                ingrediente.setUnidad(ingredienteDTO.getUnidad());
//                ingrediente.setReceta(receta); // Ahora la receta ya tiene un ID asignado
//                ingredientes.add(recetaIngredienteRepository.save(ingrediente));
//            }
//
//            // Asignar ingredientes y actualizar receta
//            receta.setIngredientes(ingredientes);
//            recetaRepository.save(receta);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Receta createReceta(RecetaDto recetaDto) {
        Receta receta = new Receta();
        receta.setName(recetaDto.getName());

        List<RecetaIngrediente> ingredientes = recetaDto.getIngredientes().stream().map(iDto -> {
            Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

            RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
            recetaIngrediente.setIngrediente(ingrediente);
            recetaIngrediente.setCantidad(iDto.getCantidad());
            recetaIngrediente.setUnidad(iDto.getUnidad());
            recetaIngrediente.setReceta(receta);
            return recetaIngrediente;
        }).collect(Collectors.toList());

        receta.setIngredientes(ingredientes);
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> findAll() {
        return recetaRepository.findAll();
    }

    @Override
    public List<Ingrediente> getIngredientesByNameReceta(String nombreReceta) {
        Receta receta = recetaRepository.findByName(nombreReceta).orElseThrow(() -> new RuntimeException("Receta con nombre '" + nombreReceta + "' no encontrada"));;
        return receta.getIngredientes().stream().map(RecetaIngrediente::getIngrediente).collect(Collectors.toList());
    }

    @Override
    public  List<IngredienteDTO> getRecetaByNombre(String nombreReceta) {
//        Receta receta = recetaRepository.findByName(nombreReceta)
//                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
//
//        List<IngredienteDTO> ingredientesDTO = receta.getIngredientes().stream()
//                .map(ri -> new IngredienteDTO(
//                        ri.getIngrediente().getName(),
//                        ri.getUnidad(),
//                        ri.getCantidad()))
//                .collect(Collectors.toList());
//
//        RecetaRespDTO recetaDTO = new RecetaRespDTO();
//        recetaDTO.setName(receta.getName());
//        recetaDTO.setIngredientes(ingredientesDTO);
//        return recetaDTO;
        Receta receta = recetaRepository.findByName(nombreReceta)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        return receta.getIngredientes().stream()
                .map(ri -> new IngredienteDTO(
                        ri.getId(),
                        ri.getIngrediente().getName(),
                        ri.getUnidad(),
                        ri.getCantidad()))
                .collect(Collectors.toList());
    }


    @Override
    public Receta getByRecetaId(Long id) {
        return recetaRepository.findById(id).orElseThrow(() -> new RuntimeException("Receta no encontrado con id: " + id));
    }

    @Override
    public boolean updateById(Long id, Receta receta) {
        return false;
    }

    @Override
    public void deleteById(Long id) {
        recetaRepository.deleteById(id);
    }
}
