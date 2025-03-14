package santa_cruz_alimento_backend.service.implementacion;

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
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
    }


    @Override
    public UserRequestDto createUser(SignupRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            Usuario usuario = new Usuario();
            usuario.setFullName(requestDto.getFull_name());
            usuario.setCi(requestDto.getCi());
            usuario.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));

            // Verifica si el nuevo rol existe antes de asignarlo
            Rol rolId = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException("Rol no encontrado con ID: " + requestDto.getRol_id()));

            usuario.setRol(rolId);
            usuario.setStatus(ReplyStatus.ACTIVO);

            Usuario createUsuario = userRepository.save(usuario);
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setId(createUsuario.getId());
            return userRequestDto;

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
    }


    @Override
    public boolean verificationCI(String ci) throws ExceptionNotFoundException {
        return userRepository.findFirstByCi(ci).isPresent();
    }

    @Override
    public List<UserResponseDto> findAll() throws ExceptionNotFoundException {
        return userRepository.findAll().stream().map(Usuario::userResponseDto).collect(Collectors.toList());

    }


    @Override
    public UserResponseDto getByUserId(Long id) throws ExceptionNotFoundException {
        try {
            Optional<Usuario> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                return optionalUser.get().userResponseDto();
            }

        }catch (Exception e){
            throw new ExceptionNotFoundException(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateByUserId(Long id, SignupRequestDto requestDto) throws ExceptionNotFoundException, Exception {
        try {
            Usuario usuario = userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Usuario con ID " + id + " no encontrado"));

            Rol rol = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException("Rol con ID " + requestDto.getRol_id() + " no encontrado"));

            usuario.setFullName(requestDto.getFull_name());
            usuario.setCi(requestDto.getCi());
            usuario.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
            //user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            usuario.setStatus(requestDto.getStatus());
            usuario.setRol(rol);

            userRepository.save(usuario);
            return true;

        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {
            Usuario usuario = userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Usuario con ID " + id + " no encontrado"));
            usuario.setStatus(ReplyStatus.INACTIVO);
            userRepository.save(usuario);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
