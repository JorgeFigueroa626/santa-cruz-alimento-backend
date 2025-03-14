package santa_cruz_alimento_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.SignupRequestDto;
import santa_cruz_alimento_backend.dto.request.UserRequestDto;
import santa_cruz_alimento_backend.dto.response.UserResponseDto;
import santa_cruz_alimento_backend.exception.ExceptionNotFoundException;
import santa_cruz_alimento_backend.service.interfaces.IUserService;

import java.util.List;

import static santa_cruz_alimento_backend.constante.Constante.*;

import static santa_cruz_alimento_backend.util.shared.ReplyMessage.*;

@RestController
@RequestMapping(API)
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(SIGNUP)
    public BaseResponse registerUser(@RequestBody SignupRequestDto requestDto) throws ExceptionNotFoundException {
        if (userService.verificationCI(requestDto.getCi())) {
            return new BaseResponse(false, HttpStatus.NOT_ACCEPTABLE,"¡Usuario con CI ya registrado! Ingresa un nuevo usuario.");
        }
        UserRequestDto createUserRequestDto = userService.createUser(requestDto);
        if (createUserRequestDto == null) {
            return new BaseResponse(false, HttpStatus.BAD_REQUEST, "Usuario not creado");
        }
        return new BaseResponse(true, createUserRequestDto, MESSAGE_SAVE);
    }

    @GetMapping(ALL_USER)
    public BaseResponse findAll() throws ExceptionNotFoundException{
        logger.info("Lista de usuarios: {} ", userService.findAll());
        List<UserResponseDto> users = userService.findAll();
        return new  BaseResponse(true, users, MESSAGE_LIST);

    }

    /*@GetMapping(ALL_USER_FILTERS)
    public BaseResponse getAllUser(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(required = false) String text
    ) throws ExceptionNotFoundException {
        //List<User> category = userService.findAllFilters(text, page, size);
        //return new BaseResponse(true, category, MESSAGE_LIST);
        return null;
    }
*/
    @DeleteMapping(BY_USER_ID)
    public BaseResponse deleteById(@PathVariable Long id) throws ExceptionNotFoundException{
        userService.deleteById(id);
        return new BaseResponse(true, null, MESSAGE_DELETE);
    }

    @GetMapping(BY_USER_ID)
    public BaseResponse getById(@PathVariable Long id) throws ExceptionNotFoundException{
        UserResponseDto user = userService.getByUserId(id);
        return new BaseResponse(true, user, MESSAGE_BY);
    }

    @PutMapping(BY_USER_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody SignupRequestDto requestDto) throws ExceptionNotFoundException, Exception{
        /*try {
            boolean update = userService.updateByUserId(id, requestDto);
            if (update) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }*/

        boolean update = userService.updateByUserId(id, requestDto);
        return new BaseResponse(true, update, MESSAGE_UPDATE);


    }
}
