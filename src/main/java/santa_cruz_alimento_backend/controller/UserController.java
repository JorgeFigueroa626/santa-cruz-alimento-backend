package santa_cruz_alimento_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.entity.dto.SignupRequestDto;
import santa_cruz_alimento_backend.entity.dto.UserDto;
import santa_cruz_alimento_backend.service.interfaces.IUserService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

@RestController
@RequestMapping(API)
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(SIGNUP)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto requestDto){
        if (userService.verificationCI(requestDto.getCi())) {
            return new ResponseEntity<>("Users already exist with thin CI", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createUserDto = userService.createUser(requestDto);
        if (createUserDto == null) {
            return new ResponseEntity<>("User not create", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(createUserDto);
    }

    @GetMapping(ALL_USER)
    public ResponseEntity<List<?>> findAll(){
        logger.info("Lista de usuarios: {} ", userService.findAll());
        try {
            return ResponseEntity.ok(userService.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(BY_USER_ID)
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(BY_USER_ID)
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            logger.info("ID de usuarios : {} ", userService.getByUserId(id));
            return ResponseEntity.ok(userService.getByUserId(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(BY_USER_ID)
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody SignupRequestDto requestDto) throws Exception{
        try {
            boolean update = userService.updateByUserId(id, requestDto);
            if (update) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
