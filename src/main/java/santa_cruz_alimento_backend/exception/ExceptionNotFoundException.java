package santa_cruz_alimento_backend.exception;

import jakarta.persistence.EntityNotFoundException;

public class ExceptionNotFoundException extends EntityNotFoundException {

    public ExceptionNotFoundException(String message) {
        super(message);
    }
}
