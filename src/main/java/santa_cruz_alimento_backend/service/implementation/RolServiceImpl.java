package santa_cruz_alimento_backend.service.implementation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.RolRequestDto;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IRolRepository;
import santa_cruz_alimento_backend.service.interfaces.IRolService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

@Service
public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    private static final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);

    @Override
    public Rol saveRol(RolRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud de registro: {}", requestDto);

            Rol rol = new Rol();
            rol.setName(requestDto.getName());
            rol.setStatus(ReplyStatus.ACTIVE.getValue());
            Rol save = rolRepository.save(rol);

            logger.info("Rol registrado : {}", save);
            return save;

        }catch ( Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public Rol getByRolId(Long id) throws ExceptionNotFoundException {
        try {
            return rolRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_ROLE_WITH_ID + id));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public Rol updateByRolId(Long id, RolRequestDto rol) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a modificar: {}", rol);

            Rol updateId = rolRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_ROLE_WITH_ID + id));
            updateId.setName(rol.getName());
            updateId.setStatus(rol.getStatus());
            Rol update = rolRepository.save(updateId);

            logger.info("Rol modificado : {}", update);
            return update;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public List<Rol> findAllRol() throws ExceptionNotFoundException {
        try {
            return rolRepository.findAll();
        }catch ( Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public void deleteByRolId(Long id) throws ExceptionNotFoundException {
        try {
            Rol updateId = rolRepository.findById(id).orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_ROLE_WITH_ID + id));
            updateId.setStatus(ReplyStatus.INACTIVE.getValue());
            rolRepository.save(updateId);
            logger.info("Rol eliminado : {}", updateId);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }
}
