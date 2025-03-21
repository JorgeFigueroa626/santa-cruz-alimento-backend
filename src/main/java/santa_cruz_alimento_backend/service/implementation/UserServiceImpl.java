package santa_cruz_alimento_backend.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.SignupRequestDto;
import santa_cruz_alimento_backend.dto.request.UserRequestDto;
import santa_cruz_alimento_backend.dto.response.UserResponseDto;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.entity.model.Usuario;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IRolRepository;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.service.interfaces.IUserService;
import santa_cruz_alimento_backend.util.enums.ReplyStatus;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /*@Override
    public UserRequestDto createAdmin(SignupRequestDto requestDto) throws ExceptionNotFoundException {
        Usuario usuario = new Usuario();
        usuario.setFullName(requestDto.getFull_name());
        usuario.setCi(requestDto.getCi());
        usuario.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
        Rol rol = rolRepository.findByName("ADMINISTRADOR");
        usuario.setRol(rol);

        Usuario createAdmin = userRepository.save(usuario);
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(createAdmin.getId());

        return userRequestDto;
    }*/


    @Override
    public UserRequestDto createUser(SignupRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            logger.info("Solicitud a registro: {}", requestDto);

            Usuario usuario = new Usuario();
            usuario.setFullName(requestDto.getFull_name());

            if (verificationCI(requestDto.getCi())) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_EXIST_CI);
            }

            usuario.setCi(requestDto.getCi());
            usuario.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));

            if (requestDto.getRol_id() == null)
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "rol");
            Rol rolId = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_ROLE_WITH_ID + requestDto.getRol_id()));

            usuario.setRol(rolId);
            usuario.setStatus(ReplyStatus.ACTIVE.getValue());

            Usuario createUsuario = userRepository.save(usuario);
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setId(createUsuario.getId());

            logger.info("Usuarios registrado: {}",userRequestDto);
            return userRequestDto;

        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean verificationCI(String ci) throws ExceptionNotFoundException {
        return userRepository.findFirstByCi(ci).isPresent();
    }

    @Override
    public List<UserResponseDto> findAll() throws ExceptionNotFoundException {
        try {

            return userRepository.findAll().stream().map(Usuario::userResponseDto).collect(Collectors.toList());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }

    }

    @Override
    public UserResponseDto getByUserId(Long id) throws ExceptionNotFoundException {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_USER_WITH_ID + id)).userResponseDto();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean updateByUserId(Long id, SignupRequestDto requestDto) throws ExceptionNotFoundException, Exception {
        try {
            logger.info("Solicitud a modificar: {}",requestDto);

            Usuario usuario = userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_USER_WITH_ID + id));

            if (requestDto.getRol_id() == null) {
                throw new ExceptionNotFoundException(ReplyMessage.MESSAGE_REQUIRED + "rol");
            }
            Rol rol = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_ROLE_WITH_ID + requestDto.getRol_id()));

            usuario.setFullName(requestDto.getFull_name());
            usuario.setCi(requestDto.getCi());
            usuario.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
            //user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            usuario.setStatus(requestDto.getStatus());
            usuario.setRol(rol);

            userRepository.save(usuario);

            logger.info("Usuario modificado. {}",usuario);
            return true;

        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Usuario usuario = userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException(ReplyMessage.MESSAGE_USER_WITH_ID  + id));
            usuario.setStatus(ReplyStatus.INACTIVE.getValue());
            userRepository.save(usuario);
            logger.info("Usuario eliminado: {}",usuario);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
