package santa_cruz_alimento_backend.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.base.BaseRequestDto;
import santa_cruz_alimento_backend.dto.request.base.DetalleBaseRequestDto;
import santa_cruz_alimento_backend.dto.response.base.BaseResponseDto;
import santa_cruz_alimento_backend.entity.model.*;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.mapper.IBaseMapper;
import santa_cruz_alimento_backend.repository.IBaseRepository;
import santa_cruz_alimento_backend.repository.IIngredienteRepository;
import santa_cruz_alimento_backend.service.interfaces.IBaseService;
import santa_cruz_alimento_backend.util.constant.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BaseServiceImpl implements IBaseService {

    @Autowired
    private IBaseRepository baseRepository;

    @Autowired
    private IIngredienteRepository ingredienteRepository;

    @Autowired
    private IBaseMapper baseMapper;

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Override
    public List<BaseResponseDto> ListBase() throws ExceptionNotFoundException {
        try {
            List<Base> bases = baseRepository.findAll();
            if (bases.isEmpty()) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_LIST_EMPTY);
            }
            return baseMapper.toBaseResponseDtoList(bases);

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public BaseResponseDto GetBaseById(Long baseId) throws ExceptionNotFoundException {
        try {
            Base base = baseRepository.findById(baseId).orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + baseId));
            BaseResponseDto responseDto = baseMapper.toBaseResponseDto(base);
            responseDto.setDetalleBases(baseMapper.toDetailBaseResponseDtoList(base.getDetalleBases()));
            return responseDto;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Base registerBase(BaseRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud registro: {}", requestDto);
            Base base = new Base();
            base.setName(requestDto.getName());
            base.setDescription(requestDto.getDescription());
            base.setStatus(ReplyStatus.ACTIVE.getValue());


            List<DetalleBase> detalleBases = requestDto.getDetalleBases().stream().map(iDto->{
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

                DetalleBase detalleBase = new DetalleBase();
                detalleBase.setIngrediente(ingrediente);
                detalleBase.setCantidad(iDto.getCantidad());
                detalleBase.setUnidad(iDto.getUnidad());
                detalleBase.setBase(base);
                return detalleBase;
            }).collect(Collectors.toList());
            base.setDetalleBases(detalleBases);

            Base save = baseRepository.save(base);
            logger.info("Base registrado: {}", save);
            return base;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public Base updateBaseById(Long baseId, BaseRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            // Buscar la base existente
            Base receta = baseRepository.findById(baseId)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BASE_WITH_ID + baseId));

            // Actualizar el nombre de la receta
            receta.setName(requestDto.getName());
            receta.setDescription(requestDto.getDescription());
            receta.setStatus(requestDto.getStatus());

            // Lista de ingredientes actuales en la base de datos
            List<DetalleBase> ingredientesActuales = receta.getDetalleBases();

            // Lista de IDs de ingredientes que vienen en la solicitud
            Set<Long> nuevosIds = requestDto.getDetalleBases().stream()
                    .map(DetalleBaseRequestDto::getIngredienteId)
                    .collect(Collectors.toSet());

            // **1️⃣ Eliminar ingredientes que ya no están en la lista**
            ingredientesActuales.removeIf(ri -> !nuevosIds.contains(ri.getIngrediente().getId()));

            // **2️⃣ Agregar o actualizar ingredientes**
            for (DetalleBaseRequestDto iDto : requestDto.getDetalleBases()) {
                Ingrediente ingrediente = ingredienteRepository.findById(iDto.getIngredienteId())
                        .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_INGREDIENT_WITH_ID + iDto.getIngredienteId()));

                // Buscar si el ingrediente ya existe en la receta
                Optional<DetalleBase> existente = ingredientesActuales.stream()
                        .filter(ri -> ri.getIngrediente().getId().equals(ingrediente.getId()))
                        .findFirst();

                if (existente.isPresent()) {
                    // **Actualizar cantidad y unidad si el ingrediente ya existe**
                    existente.get().setCantidad(iDto.getCantidad());
                    existente.get().setUnidad(iDto.getUnidad());
                } else {
                    // **Agregar nuevo ingrediente si no está en la lista**
                    DetalleBase nuevoRI = new DetalleBase();
                    nuevoRI.setBase(receta);
                    nuevoRI.setIngrediente(ingrediente);
                    nuevoRI.setCantidad(iDto.getCantidad());
                    nuevoRI.setUnidad(iDto.getUnidad());
                    ingredientesActuales.add(nuevoRI);
                }
            }

            // Guardar la receta con los ingredientes actualizados
            receta.setDetalleBases(ingredientesActuales);
            Base update = baseRepository.save(receta);

            logger.info("Receta modificado : {}", update);
            return update;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void deleteBaseById(Long baseId) throws ExceptionNotFoundException {
        try {
            Base base = baseRepository.findById(baseId)
                    .orElseThrow(()-> new ExceptionNotFoundException(ReplyMessage.MESSAGE_BASE_WITH_ID + baseId));
            base.setStatus(ReplyStatus.INACTIVE.getValue());
            baseRepository.save(base);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


}
