package santa_cruz_alimento_backend.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.SignupRequestDto;
import santa_cruz_alimento_backend.entity.dto.UserDto;
import santa_cruz_alimento_backend.entity.dto.UserResponseDto;
import santa_cruz_alimento_backend.entity.model.Product;
import santa_cruz_alimento_backend.entity.model.Rol;
import santa_cruz_alimento_backend.entity.model.User;
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
    public UserDto createAdmin(SignupRequestDto requestDto) {
        User user = new User();
        user.setFullName(requestDto.getFull_name());
        user.setCi(requestDto.getCi());
        user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
        Rol rol = rolRepository.findByName("ADMINISTRADOR");
        user.setRol(rol);

        User createAdmin = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createAdmin.getId());

        return userDto;
    }

    @Override
    public UserDto createUser(SignupRequestDto requestDto) {
        User user = new User();
        user.setFullName(requestDto.getFull_name());
        user.setCi(requestDto.getCi());
        user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));

        // Verifica si el nuevo rol existe antes de asignarlo
        Rol rolId = rolRepository.findById(requestDto.getRol_id())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + requestDto.getRol_id()));

        user.setRol(rolId);

        User createUser = userRepository.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());
        return userDto;
    }


    @Override
    public boolean verificationCI(String ci) {
        return userRepository.findFirstByCi(ci).isPresent();
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(User::userResponseDto).collect(Collectors.toList());

    }


    @Override
    public UserResponseDto getByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get().userResponseDto();
        }
        return null;
    }

    @Override
    public boolean updateByUserId(Long id, SignupRequestDto requestDto) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario con ID " + id + " no encontrado"));

        Rol rol = rolRepository.findById(requestDto.getRol_id())
                .orElseThrow(() -> new Exception("Rol con ID " + requestDto.getRol_id() + " no encontrado"));

        user.setFullName(requestDto.getFull_name());
        user.setCi(requestDto.getCi());
        user.setPassword(new BCryptPasswordEncoder().encode(requestDto.getPassword()));
        //user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRol(rol);

        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
