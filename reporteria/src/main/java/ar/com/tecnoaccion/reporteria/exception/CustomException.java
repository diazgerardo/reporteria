package ar.com.tecnoaccion.reporteria.exception;

import ar.com.tecnoaccion.reporteria.utils.DefinedErrors;

import java.util.Collection;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 7154635638168252203L;
	private Integer errorCode;

    public CustomException(Integer code, String message) {
        super(message);
        this.errorCode = code;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Exception exception) {
        super(message, exception);
    }


    public CustomException(DefinedErrors error) {
        this(error.getErrorCode(), error.getErrorMessage());
    }

    public static void assertNotEmpty(Collection<?> objects, DefinedErrors error) {
        if (objects.isEmpty()) {
            throw new CustomException(error.getErrorCode(), error.getErrorMessage());
        }
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
