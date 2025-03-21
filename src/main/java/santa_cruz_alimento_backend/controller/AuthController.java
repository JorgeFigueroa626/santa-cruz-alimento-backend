package santa_cruz_alimento_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.AuthRequestDto;
import santa_cruz_alimento_backend.dto.response.AuthResponseDto;
import santa_cruz_alimento_backend.dto.request.SignupRequestDto;
import santa_cruz_alimento_backend.dto.request.UserRequestDto;
import santa_cruz_alimento_backend.entity.model.Usuario;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.security.JWTUtil;
import santa_cruz_alimento_backend.security.UserDetailsServiceImpl;
import santa_cruz_alimento_backend.service.interfaces.IUserService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.Optional;

import static santa_cruz_alimento_backend.constante.ConstantEntity.*;

@RestController
@RequestMapping(AUTH)
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private IUserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(SIGNUP_USER)
    public BaseResponse registerUser(@RequestBody SignupRequestDto requestDto){
        UserRequestDto createUserRequestDto = userService.createUser(requestDto);
        return new BaseResponse(true, createUserRequestDto, ReplyMessage.MESSAGE_SAVE);
    }

    @PostMapping(LOGIN)
    public BaseResponse authentication(@RequestBody AuthRequestDto requestDto) throws BadCredentialsException, ExceptionNotFoundException {
        try {
            logger.info("Solicitud de inicio session: {}", requestDto);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getCi(), requestDto.getPassword()));
        }catch (Exception e){
            throw  new ExceptionNotFoundException("Incorrect CI o Password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getCi());
        Optional<Usuario> optionalUser = userRepository.findFirstByCi(userDetails.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        if (optionalUser.isPresent()) {
            authResponseDto.setUser_id(optionalUser.get().getId());
            authResponseDto.setRol(optionalUser.get().getRol().getName());
            authResponseDto.setToken(jwt);
        }
        logger.info("Inicio session : {}", authResponseDto);
        return new BaseResponse(true, authResponseDto, ReplyMessage.MESSAGE_AUTHENTICATION);
    }
}
