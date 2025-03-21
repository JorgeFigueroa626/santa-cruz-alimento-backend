package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import santa_cruz_alimento_backend.util.message.ReplyMessage;

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

    private static final Logger logger = LoggerFactory.getLogger(RecetaServiceImpl.class);

    @Override
    public Receta createReceta(RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", recetaRequestDTO);
            Receta receta = new Receta();
            receta.setName(recetaRequestDTO.getName());

            List<DetalleRecetas> detallesDto = recetaRequestDTO.getIngredientes().stream().map(iDto -> {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

                DetalleRecetas recetaIngrediente = new DetalleRecetas();
                recetaIngrediente.setIngrediente(ingrediente);
                recetaIngrediente.setCantidad(iDto.getCantidad());
                recetaIngrediente.setUnidad(iDto.getUnidad());
                recetaIngrediente.setReceta(receta);
                return recetaIngrediente;
            }).collect(Collectors.toList());

            receta.setDetalleRecetas(detallesDto);
            receta.setStatus(ReplyStatus.ACTIVE.getValue());

            Receta save = recetaRepository.save(receta);
            logger.info("Receta registrado : {}", save);
            return save;
        }catch (Exception e){
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
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
                    detalleDto.setIngredienteId(detalle.getIngrediente().getId());
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
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_NAME + nombreReceta));

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
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + id));

            RecetaResponseDto dto = new RecetaResponseDto();
            dto.setId(receta.getId());
            dto.setName(receta.getName());
            dto.setStatus(receta.getStatus());

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

            dto.setDetalleRecetas(detallesDto);

            return dto;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Receta updateById(Long recetaId, RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a modificar: {}", recetaRequestDTO);

            // Buscar la receta existente
            Receta receta = recetaRepository.findById(recetaId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + recetaId));

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
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

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
            Receta update = recetaRepository.save(receta);

            logger.info("Receta modificado : {}", update);
            return update;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Receta receta = recetaRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + id));
            receta.setStatus(ReplyStatus.INACTIVE.getValue());
            recetaRepository.save(receta);
            logger.info("Receta eliminado : {}", receta);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }
}
