package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.request.auth.SignupRequestDto;
import santa_cruz_alimento_backend.dto.request.user.UserRequestDto;
import santa_cruz_alimento_backend.dto.response.user.UserResponseDto;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IUserService {

    //UserRequestDto createAdmin(SignupRequestDto requestDto) throws ExceptionNotFoundException;

    /// authentications
    UserRequestDto createUser(SignupRequestDto signupDto) throws ExceptionNotFoundException;

    boolean verificationCI(String ci) throws ExceptionNotFoundException;

    /// users
    List<UserResponseDto> findAll() throws ExceptionNotFoundException;

    UserResponseDto getByUserId(Long id) throws ExceptionNotFoundException;

    boolean updateByUserId(Long id, SignupRequestDto requestDto) throws ExceptionNotFoundException, Exception;

    void deleteById(Long id)throws ExceptionNotFoundException;
}
