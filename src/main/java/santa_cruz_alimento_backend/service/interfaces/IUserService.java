package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.entity.dto.SignupRequestDto;
import santa_cruz_alimento_backend.entity.dto.UserDto;
import santa_cruz_alimento_backend.entity.dto.UserResponseDto;
import santa_cruz_alimento_backend.entity.model.User;

import java.util.List;

@Service
public interface IUserService {

    UserDto createAdmin(SignupRequestDto requestDto);

    UserDto createUser(SignupRequestDto signupDto);

    boolean verificationCI(String ci);

    List<UserResponseDto> findAll();

    UserResponseDto getByUserId(Long id);

    boolean updateByUserId(Long id, SignupRequestDto requestDto) throws Exception;

    void deleteById(Long id);
}
