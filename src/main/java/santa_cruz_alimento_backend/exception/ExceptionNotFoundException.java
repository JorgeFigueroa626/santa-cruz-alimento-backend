package santa_cruz_alimento_backend.exception;

import jakarta.persistence.EntityNotFoundException;

public class ExceptionNotFoundException extends EntityNotFoundException {

    public ExceptionNotFoundException(String message) {
        super(message);
    }

    public ExceptionNotFoundException(Long id) {
        this(String.format("No se pudo encontrar la Entidad con id: %d", id));
    }
}
