package ar.com.tecnoaccion.reporteria.exception;

import ar.com.tecnoaccion.reporteria.utils.DefinedErrors;

import java.util.Collection;

public class CuponException extends RuntimeException {
    private Integer errorCode;

    public CuponException(Integer code, String message){
        super(message);
        this.errorCode = code;
    }

    public CuponException(String message) {
        super(message);
    }

    public CuponException(String message, Exception exception) {
        super(message, exception);
    }


    public CuponException(DefinedErrors error) {
        this(error.getErrorCode(), error.getErrorMessage());
    }

    public static void assertNotEmpty(Collection<?> objects, DefinedErrors error) {
        if (objects.isEmpty()) {
            throw new CuponException(error.getErrorCode(), error.getErrorMessage());
        }
    }

    public Integer getErrorCode() { return errorCode; }
}