package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.SignupRequestDto;
import santa_cruz_alimento_backend.dto.Request.UserRequestDto;
import santa_cruz_alimento_backend.dto.Response.UserResponseDto;
import santa_cruz_alimento_backend.entity.model.Category;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.entity.model.User;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IRolRepository;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.service.interfaces.IUserService;

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
        User user = new User();
        user.setFullName(requestDto.getFull_name());
        user.setCi(requestDto.getCi());
        user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
        Rol rol = rolRepository.findByName("ADMINISTRADOR");
        user.setRol(rol);

        User createAdmin = userRepository.save(user);
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(createAdmin.getId());

        return userRequestDto;
    }


    @Override
    public UserRequestDto createUser(SignupRequestDto requestDto) throws ExceptionNotFoundException {
        try {
            User user = new User();
            user.setFullName(requestDto.getFull_name());
            user.setCi(requestDto.getCi());
            user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));

            // Verifica si el nuevo rol existe antes de asignarlo
            Rol rolId = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException("Rol no encontrado con ID: " + requestDto.getRol_id()));

            user.setRol(rolId);

            User createUser = userRepository.save(user);
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setId(createUser.getId());
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
        return userRepository.findAll().stream().map(User::userResponseDto).collect(Collectors.toList());

    }


    @Override
    public UserResponseDto getByUserId(Long id) throws ExceptionNotFoundException {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
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
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ExceptionNotFoundException("Usuario con ID " + id + " no encontrado"));

            Rol rol = rolRepository.findById(requestDto.getRol_id())
                    .orElseThrow(() -> new ExceptionNotFoundException("Rol con ID " + requestDto.getRol_id() + " no encontrado"));

            user.setFullName(requestDto.getFull_name());
            user.setCi(requestDto.getCi());
            user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
            //user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            user.setRol(rol);

            userRepository.save(user);
            return true;

        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws ExceptionNotFoundException {
        try {

            userRepository.deleteById(id);
        }catch (Exception e){
            throw  new ExceptionNotFoundException(e.getMessage());
        }
    }
}
