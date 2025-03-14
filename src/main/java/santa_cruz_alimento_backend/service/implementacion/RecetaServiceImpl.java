package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.*;
import santa_cruz_alimento_backend.dto.response.*;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IDetalleRecetasRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;

import java.io.IOException;
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
    private IDetalleRecetasRepository detalleRecetasRepository;

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
    public Receta createReceta(RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {

            Receta receta = new Receta();
            receta.setName(recetaRequestDTO.getName());

            List<DetalleRecetas> detallesDto = recetaRequestDTO.getIngredientes().stream().map(iDto -> {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el ID : " + iDto.getIngredienteId()));

                DetalleRecetas recetaIngrediente = new DetalleRecetas();
                recetaIngrediente.setIngrediente(ingrediente);
                recetaIngrediente.setCantidad(iDto.getCantidad());
                recetaIngrediente.setUnidad(iDto.getUnidad());
                recetaIngrediente.setReceta(receta);
                return recetaIngrediente;
            }).collect(Collectors.toList());

            receta.setDetalleRecetas(detallesDto);
            receta.setStatus(ReplyStatus.ACTIVO);

            return recetaRepository.save(receta);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<RecetaResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            //return recetaRepository.findAll();
            List<Receta> recetas = recetaRepository.findAll();

            return recetas.stream().map(receta -> {
                RecetaResponseDto dto = new RecetaResponseDto();
                dto.setId(receta.getId());
                dto.setName(receta.getName());
                dto.setStatus(receta.getStatus());

                // Convertir detalles de receta a DTO
                List<DetalleRecetaResponseDto> detallesDto = receta.getDetalleRecetas().stream().map(detalle -> {
                    DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
                    detalleDto.setId(detalle.getId());
                    detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                    detalleDto.setCantidad(detalle.getCantidad());
                    detalleDto.setUnidad(detalle.getUnidad());
                    return detalleDto;
                }).collect(Collectors.toList());

                dto.setDetalleRecetas(detallesDto);

                return dto;
            }).collect(Collectors.toList());

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public  List<IngredientesResponseDto> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException {
        try {
            Receta receta = recetaRepository.findByName(nombreReceta)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con el Nombre : " + nombreReceta));

            return receta.getDetalleRecetas().stream()
                    .map(ri -> new IngredientesResponseDto(
                            ri.getId(),
                            ri.getIngrediente().getName(),
                            ri.getCantidad(),
                            ri.getIngrediente().getStock(),
                            ri.getUnidad(),
                            ri.getIngrediente().getStatus()
                            ))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public RecetaResponseDto getByRecetaId(Long id) throws ExceptionNotFoundException {
        try {
            Receta receta = recetaRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con ID: " + id));

            RecetaResponseDto dto = new RecetaResponseDto();
            dto.setId(receta.getId());
            dto.setName(receta.getName());
            dto.setStatus(receta.getStatus());

            // Convertir detalles de recera a DTO
            List<DetalleRecetaResponseDto> detallesDto = receta.getDetalleRecetas().stream().map(detalle -> {
                DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
                detalleDto.setId(detalle.getId());
                detalleDto.setNombre_ingrediente(detalle.getIngrediente().getName());
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setUnidad(detalle.getUnidad());
                return detalleDto;
            }).collect(Collectors.toList());

            dto.setDetalleRecetas(detallesDto);

            return dto;
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
        //return recetaRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrado con id: " + id));
    }

    @Override
    public Receta updateById(Long recetaId, RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            // Buscar la receta existente
            Receta receta = recetaRepository.findById(recetaId)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con el ID : " + recetaId));

            // Actualizar el nombre de la receta
            receta.setName(recetaRequestDTO.getName());
            receta.setStatus(recetaRequestDTO.getStatus());

            // Lista de ingredientes actuales en la base de datos
            List<DetalleRecetas> ingredientesActuales = receta.getDetalleRecetas();

            // Lista de IDs de ingredientes que vienen en la solicitud
            Set<Long> nuevosIds = recetaRequestDTO.getIngredientes().stream()
                    .map(DetalleRecetasRequestDto::getIngredienteId)
                    .collect(Collectors.toSet());

            // **1️⃣ Eliminar ingredientes que ya no están en la lista**
            ingredientesActuales.removeIf(ri -> !nuevosIds.contains(ri.getIngrediente().getId()));

            // **2️⃣ Agregar o actualizar ingredientes**
            for (DetalleRecetasRequestDto iDto : recetaRequestDTO.getIngredientes()) {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException("Ingrediente no encontrado con el ID : " + iDto.getIngredienteId()));

                // Buscar si el ingrediente ya existe en la receta
                Optional<DetalleRecetas> existente = ingredientesActuales.stream()
                        .filter(ri -> ri.getIngrediente().getId().equals(ingrediente.getId()))
                        .findFirst();

                if (existente.isPresent()) {
                    // **Actualizar cantidad y unidad si el ingrediente ya existe**
                    existente.get().setCantidad(iDto.getCantidad());
                    existente.get().setUnidad(iDto.getUnidad());
                } else {
                    // **Agregar nuevo ingrediente si no está en la lista**
                    DetalleRecetas nuevoRI = new DetalleRecetas();
                    nuevoRI.setReceta(receta);
                    nuevoRI.setIngrediente(ingrediente);
                    nuevoRI.setCantidad(iDto.getCantidad());
                    nuevoRI.setUnidad(iDto.getUnidad());
                    ingredientesActuales.add(nuevoRI);
                }
            }

            // Guardar la receta con los ingredientes actualizados
            receta.setDetalleRecetas(ingredientesActuales);
            return recetaRepository.save(receta);

        } catch (Exception e) {
            throw new ExceptionNotFoundException("Error al actualizar la receta: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Receta receta = recetaRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Receta no encontrada con el ID : " + id));
            receta.setStatus(ReplyStatus.INACTIVO);
            recetaRepository.save(receta);
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
