package santa_cruz_alimento_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import santa_cruz_alimento_backend.dto.base.BaseResponse;
import santa_cruz_alimento_backend.dto.request.auth.SignupRequestDto;
import santa_cruz_alimento_backend.dto.request.user.UserRequestDto;
import santa_cruz_alimento_backend.dto.response.user.UserResponseDto;
import santa_cruz_alimento_backend.service.interfaces.IUserService;
import santa_cruz_alimento_backend.util.message.ReplyMessage;

import java.util.List;

import static santa_cruz_alimento_backend.util.constant.ConstantEntity.*;

@RestController
@RequestMapping(API)
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(SIGNUP)
    public BaseResponse registerUser(@RequestBody SignupRequestDto requestDto)  {
        UserRequestDto createUserRequestDto = userService.createUser(requestDto);
        if (createUserRequestDto == null) {
            return new BaseResponse(false, HttpStatus.BAD_REQUEST, ReplyMessage.MESSAGE_FAILED);
        }
        return new BaseResponse(true, createUserRequestDto, ReplyMessage.MESSAGE_SAVE);
    }

    @GetMapping(ALL_USER)
    public BaseResponse findAll() {
        List<UserResponseDto> users = userService.findAll();
        return new  BaseResponse(true, users, ReplyMessage.MESSAGE_LIST);

    }

    @DeleteMapping(BY_USER_ID)
    public BaseResponse deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new BaseResponse(true, null, ReplyMessage.MESSAGE_DELETE);
    }

    @GetMapping(BY_USER_ID)
    public BaseResponse getById(@PathVariable Long id) {
        var user = userService.getByUserId(id);
        return new BaseResponse(true, user, ReplyMessage.MESSAGE_BY);
    }

    @PutMapping(BY_USER_ID)
    public BaseResponse updateById(@PathVariable Long id, @RequestBody SignupRequestDto requestDto) throws Exception{
        boolean update = userService.updateByUserId(id, requestDto);
        return new BaseResponse(true, update, ReplyMessage.MESSAGE_UPDATE);
    }
}
