package santa_cruz_alimento_backend.service.interfaces;

import org.springframework.stereotype.Service;
import santa_cruz_alimento_backend.dto.Request.SignupRequestDto;
import santa_cruz_alimento_backend.dto.Request.UserRequestDto;
import santa_cruz_alimento_backend.dto.Response.UserResponseDto;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;

import java.util.List;

@Service
public interface IUserService {

    UserRequestDto createAdmin(SignupRequestDto requestDto) throws ExceptionNotFoundException;

    UserRequestDto createUser(SignupRequestDto signupDto) throws ExceptionNotFoundException;

    boolean verificationCI(String ci) throws ExceptionNotFoundException;

    List<UserResponseDto> findAll() throws ExceptionNotFoundException;

    //List<UserResponseDto> findAllFilters(String text, Integer page, Integer size) throws ExceptionNotFoundException;

    UserResponseDto getByUserId(Long id) throws ExceptionNotFoundException;

    boolean updateByUserId(Long id, SignupRequestDto requestDto) throws ExceptionNotFoundException, Exception;

    void deleteById(Long id)throws ExceptionNotFoundException;
}
