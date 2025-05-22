package gm.rh.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class RecursoNoEcontradoExcepcion extends RuntimeException {
    public RecursoNoEcontradoExcepcion(String message) {
        super(message);
    }
}
