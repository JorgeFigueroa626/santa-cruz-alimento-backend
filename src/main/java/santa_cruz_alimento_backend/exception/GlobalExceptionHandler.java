package santa_cruz_alimento_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import santa_cruz_alimento_backend.dto.base.BaseResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        BaseResponse response = new BaseResponse(false, null, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleGenericException(ExceptionNotFoundException ex) {
        BaseResponse response = new BaseResponse(false, null, "Error interno del servidor: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExceptionNotFoundException.class)
    public ResponseEntity<BaseResponse> handleExceptionNotFoundException(Exception ex) {
        BaseResponse response = new BaseResponse(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(ExceptionNotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(ExceptionNotFoundException ex) {
        BaseResponse response = new BaseResponse(false, null, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }*/

}
