package santa_cruz_alimento_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santa_cruz_alimento_backend.entity.dto.AuthRequestDto;
import santa_cruz_alimento_backend.entity.dto.AuthResponseDto;
import santa_cruz_alimento_backend.entity.dto.SignupRequestDto;
import santa_cruz_alimento_backend.entity.dto.UserDto;
import santa_cruz_alimento_backend.entity.model.User;
import santa_cruz_alimento_backend.repository.IUserRepository;
import santa_cruz_alimento_backend.security.JWTUtil;
import santa_cruz_alimento_backend.security.UserDetailsServiceImpl;
import santa_cruz_alimento_backend.service.interfaces.IUserService;

import java.util.Optional;

import static santa_cruz_alimento_backend.Constante.Constante.*;

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

    @PostMapping(SIGNUP_ADMIN)
    public ResponseEntity<?> registerAdmin(@RequestBody SignupRequestDto requestDto){
        if (userService.verificationCI(requestDto.getCi())) {
            return new ResponseEntity<>("Users already exist with thin CI", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createUserDto = userService.createAdmin(requestDto);
        if (createUserDto == null) {
            return new ResponseEntity<>("User not create", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(createUserDto);
    }

    @PostMapping(SIGNUP)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto requestDto){
        logger.info("Solicitud recibida para crear usuario: {}", requestDto.getFull_name(), requestDto.getCi(), requestDto.getPassword(), requestDto.getRol_id());
        if (userService.verificationCI(requestDto.getCi())) {
            return new ResponseEntity<>("Users already exist with thin CI", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createUserDto = userService.createUser(requestDto);
        if (createUserDto == null) {
            return new ResponseEntity<>("User not create", HttpStatus.BAD_REQUEST);
        }
        logger.info("Usuario creado : {}", createUserDto);
        return ResponseEntity.ok(createUserDto);
    }

    @PostMapping(LOGIN)
    public AuthResponseDto authentication(@RequestBody AuthRequestDto requestDto) throws BadCredentialsException {
        logger.info("Solicitud recibida para iniciar session: {}", requestDto.getCi(), requestDto.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getCi(), requestDto.getPassword()));
        }catch (BadCredentialsException e){
            throw  new BadCredentialsException("Incorrect CI o Password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getCi());
        Optional<User> optionalUser = userRepository.findFirstByCi(userDetails.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        if (optionalUser.isPresent()) {
            authResponseDto.setUser_id(optionalUser.get().getId());
            authResponseDto.setRol(optionalUser.get().getRol().getName());
            authResponseDto.setToken(jwt);
        }
        logger.info("Inicio session el usuario : {}", authResponseDto);
        return authResponseDto;
    }
}
