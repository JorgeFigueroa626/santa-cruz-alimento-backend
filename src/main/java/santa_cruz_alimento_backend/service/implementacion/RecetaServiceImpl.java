package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.RecetaIngredienteDTO;
import santa_cruz_alimento_backend.dto.Response.IngredienteDTO;
import santa_cruz_alimento_backend.dto.Request.RecetaRequestDTO;
import santa_cruz_alimento_backend.entity.model.Ingrediente;
import santa_cruz_alimento_backend.entity.model.Receta;
import santa_cruz_alimento_backend.entity.model.RecetaIngrediente;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IRecetaIngredienteRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecetaServiceImpl implements IRecetaService {

    @Autowired
    private IRecetaRepository recetaRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IRecetaIngredienteRepository recetaIngredienteRepository;

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
    public Receta createReceta(RecetaRequestDTO recetaRequestDTO) throws ExceptionNotFoundException {
        try {

            Receta receta = new Receta();
            receta.setName(recetaRequestDTO.getName());

            List<RecetaIngrediente> ingredientes = recetaRequestDTO.getIngredientes().stream().map(iDto -> {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado"));

                RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
                recetaIngrediente.setIngrediente(ingrediente);
                recetaIngrediente.setCantidad(iDto.getCantidad());
                recetaIngrediente.setUnidad(iDto.getUnidad());
                recetaIngrediente.setReceta(receta);
                return recetaIngrediente;
            }).collect(Collectors.toList());

            receta.setIngredientes(ingredientes);

            return recetaRepository.save(receta);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Receta> findAll() throws ExceptionNotFoundException {
        try {
            return recetaRepository.findAll();

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Ingrediente> getIngredientesByNameReceta(String nombreReceta) throws ExceptionNotFoundException {
        try {

            Receta receta = recetaRepository.findByName(nombreReceta).orElseThrow(() -> new ExceptionNotFoundException("Receta con nombre '" + nombreReceta + "' no encontrada"));;
            return receta.getIngredientes().stream().map(RecetaIngrediente::getIngrediente).collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public  List<IngredienteDTO> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException {
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
        try {

            Receta receta = recetaRepository.findByName(nombreReceta)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada"));

            return receta.getIngredientes().stream()
                    .map(ri -> new IngredienteDTO(
                            ri.getId(),
                            ri.getIngrediente().getName(),
                            ri.getUnidad(),
                            ri.getCantidad()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public Receta getByRecetaId(Long id) throws ExceptionNotFoundException {
        return recetaRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrado con id: " + id));
    }

    @Override
    public Receta updateById(Long recetaId, RecetaRequestDTO recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            // Buscar la receta existente
            Receta receta = recetaRepository.findById(recetaId)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada"));

            // Actualizar el nombre de la receta
            receta.setName(recetaRequestDTO.getName());

            // Lista de ingredientes actuales en la base de datos
            List<RecetaIngrediente> ingredientesActuales = receta.getIngredientes();

            // Lista de IDs de ingredientes que vienen en la solicitud
            Set<Long> nuevosIds = recetaRequestDTO.getIngredientes().stream()
                    .map(RecetaIngredienteDTO::getIngredienteId)
                    .collect(Collectors.toSet());

            // **1️⃣ Eliminar ingredientes que ya no están en la lista**
            ingredientesActuales.removeIf(ri -> !nuevosIds.contains(ri.getIngrediente().getId()));

            // **2️⃣ Agregar o actualizar ingredientes**
            for (RecetaIngredienteDTO iDto : recetaRequestDTO.getIngredientes()) {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado"));

                // Buscar si el ingrediente ya existe en la receta
                Optional<RecetaIngrediente> existente = ingredientesActuales.stream()
                        .filter(ri -> ri.getIngrediente().getId().equals(ingrediente.getId()))
                        .findFirst();

                if (existente.isPresent()) {
                    // **Actualizar cantidad y unidad si el ingrediente ya existe**
                    existente.get().setCantidad(iDto.getCantidad());
                    existente.get().setUnidad(iDto.getUnidad());
                } else {
                    // **Agregar nuevo ingrediente si no está en la lista**
                    RecetaIngrediente nuevoRI = new RecetaIngrediente();
                    nuevoRI.setReceta(receta);
                    nuevoRI.setIngrediente(ingrediente);
                    nuevoRI.setCantidad(iDto.getCantidad());
                    nuevoRI.setUnidad(iDto.getUnidad());
                    ingredientesActuales.add(nuevoRI);
                }
            }

            // Guardar la receta con los ingredientes actualizados
            receta.setIngredientes(ingredientesActuales);
            return recetaRepository.save(receta);

        } catch (Exception e) {
            throw new ExceptionNotFoundException("Error al actualizar la receta: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            recetaRepository.deleteById(id);

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
