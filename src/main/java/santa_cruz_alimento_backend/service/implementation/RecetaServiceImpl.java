package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.recipe.DetalleRecetasRequestDto;
import santa_cruz_alimento_backend.dto.request.recipe.RecetaRequesDto;
import santa_cruz_alimento_backend.dto.response.base.DetalleBaseResponseDto;
import santa_cruz_alimento_backend.dto.response.ingredient.IngredienteResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.DetalleRecetaResponseDto;
import santa_cruz_alimento_backend.dto.response.recipe.RecetaResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IRecipeMapper;
import santa_cruz_alimento_backend.repository.IBaseRepository;
import santa_cruz_alimento_backend.repository.IDetalleRecetasRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.repository.IRecetaRepository;
import santa_cruz_alimento_backend.service.interfaces.IRecetaService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

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
    private IBaseRepository baseRepository;

    @Autowired
    private IRecipeMapper recipeMapper;

    private static final Logger logger = LoggerFactory.getLogger(RecetaServiceImpl.class);

    @Transactional
    @Override
    public Receta createReceta(RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", recetaRequestDTO);
            Receta receta = new Receta();
            receta.setName(recetaRequestDTO.getName());
            receta.setStatus(ReplyStatus.ACTIVE.getValue());

            List<DetalleRecetas> detallesDto = recetaRequestDTO.getDetalleRecetas().stream().map(iDto -> {
                Base base = baseRepository.findById(iDto.getBaseId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BASE_WITH_ID + iDto.getBaseId()));

                DetalleRecetas detalleRecetas = new DetalleRecetas();
                detalleRecetas.setBase(base);
                detalleRecetas.setReceta(receta);
                return detalleRecetas;
            }).collect(Collectors.toList());

            receta.setDetalleRecetas(detallesDto);

            Receta save = recetaRepository.save(receta);
            logger.info("Receta registrado : {}", save);
            return save;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public List<RecetaResponseDto> findAll() throws ExceptionNotFoundException {
        try {
            List<Receta> recetas = recetaRepository.findAll();
            if (recetas.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return recipeMapper.toRecipeResponseDtoList(recetas);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public  List<IngredienteResponseDto> getRecetaByNombre(String nombreReceta) throws ExceptionNotFoundException {
        /*try {
            Receta receta = recetaRepository.findByName(nombreReceta)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_NAME + nombreReceta));

            return receta.getDetalleRecetas().stream()
                    .map(ri -> new IngredienteResponseDto(
                            ri.getId(),
                            ri.getIngrediente().getName(),
                            ri.getCantidad(),
                            ri.getUnidad(),
                            ri.getIngrediente().getStatus()
                            ))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }*/
        return null;
    }


    public RecetaResponseDto getByRecetaId(Long id) throws ExceptionNotFoundException {
        try {
            Receta receta = recetaRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + id));

            // Mapeo de Receta a DTO
            RecetaResponseDto dto = recipeMapper.toRecipeResponseDto(receta);

            // Mapeo de DetalleRecetas
            List<DetalleRecetaResponseDto> detalleRecetasDto = receta.getDetalleRecetas()
                    .stream()
                    .map(detalleReceta -> {
                        DetalleRecetaResponseDto detalleRecetaDto = recipeMapper.toRecipeDetailResponseDto(detalleReceta);

                        // Mapeo de DetalleBase dentro de DetalleRecetas
                        List<DetalleBaseResponseDto> detalleBasesDto = detalleReceta.getBase().getDetalleBases()
                                .stream()
                                .map(recipeMapper::toDetalleBaseResponseDto)
                                .collect(Collectors.toList());

                        detalleRecetaDto.setDetalleBases(detalleBasesDto);
                        return detalleRecetaDto;
                    })
                    .collect(Collectors.toList());

            dto.setDetalleRecetas(detalleRecetasDto);

            return dto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Transactional
    @Override
    public Receta updateById(Long recetaId, RecetaRequesDto recetaRequestDTO) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a modificar: {}", recetaRequestDTO);

            // Buscar la receta existente
            Receta receta = recetaRepository.findById(recetaId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_RECIPE_WITH_ID + recetaId));

            // Actualizar los datos básicos de la receta
            receta.setName(recetaRequestDTO.getName());
            receta.setStatus(recetaRequestDTO.getStatus());

            // Lista actual de DetalleRecetas en la base de datos
            List<DetalleRecetas> detalleRecetasActuales = receta.getDetalleRecetas();

            // Lista de IDs de bases que vienen en la solicitud
            Set<Long> nuevosIds = recetaRequestDTO.getDetalleRecetas().stream()
                    .map(DetalleRecetasRequestDto::getBaseId)
                    .collect(Collectors.toSet());

            // **1️⃣ Eliminar bases que ya no están en la solicitud**
            detalleRecetasActuales.removeIf(detalle -> !nuevosIds.contains(detalle.getBase().getId()));

            // **2️⃣ Agregar nuevas bases si no existen**
            for (DetalleRecetasRequestDto detalleDto : recetaRequestDTO.getDetalleRecetas()) {
                Long baseId = detalleDto.getBaseId();
                Base base = baseRepository.findById(baseId)
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BASE_WITH_ID + baseId));

                // Verificar si la base ya está en la receta
                boolean existe = detalleRecetasActuales.stream()
                        .anyMatch(detalle -> detalle.getBase().getId().equals(baseId));

                // Agregar solo si no existe
                if (!existe) {
                    DetalleRecetas nuevoDetalle = new DetalleRecetas();
                    nuevoDetalle.setReceta(receta);
                    nuevoDetalle.setBase(base);
                    detalleRecetasActuales.add(nuevoDetalle);
                }
            }

            // Actualizar la receta con la nueva lista de DetalleRecetas
            receta.setDetalleRecetas(detalleRecetasActuales);
            Receta recetaActualizada = recetaRepository.save(receta);

            logger.info("Receta modificada: {}", recetaActualizada);
            return recetaActualizada;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
        /*try {
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
        }*/
    }

    @Transactional
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

    /*private RecetaResponseDto convertirRecetaADto(Receta receta) {
        RecetaResponseDto dto = new RecetaResponseDto();
        dto.setId(receta.getId());
        dto.setName(receta.getName());
        dto.setStatus(receta.getStatus());
        // Convertir detalles de receta a DTO usando el método privado
        dto.setDetalleRecetas(convertirDetallesRecetaADto(receta.getDetalleRecetas()));

        return dto;
    }

    private List<DetalleRecetaResponseDto> convertirDetallesRecetaADto(List<DetalleRecetas> detallesRecetas) {
        return detallesRecetas.stream().map(detalle -> {
            DetalleRecetaResponseDto detalleDto = new DetalleRecetaResponseDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setBaseId(detalle.getBase().getId());
            detalleDto.setNombre_base(detalle.getBase().getName());
            List<DetalleBaseResponseDto> detalleBaseResponseDtos = detalleDto.getDetalleBases().stream().map(bstDTO ->{
                Ingrediente ingrediente = ingredienteRepository.findById(bstDTO.getIngredienteId())
                        .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + bstDTO.getIngredienteId()));
                DetalleBaseResponseDto responseDto = new DetalleBaseResponseDto();
                responseDto.setId(bstDTO.getId());
                responseDto.setIngredienteId(ingrediente.getId());
                responseDto.setNombre_ingrediente(ingrediente.getName());
                responseDto.setCantidad(bstDTO.getCantidad());
                responseDto.setUnidad(bstDTO.getUnidad());
                return  responseDto;
            }).collect(Collectors.toList());
            detalleDto.setDetalleBases(detalleBaseResponseDtos);
            return detalleDto;
        }).collect(Collectors.toList());
    }*/
}
