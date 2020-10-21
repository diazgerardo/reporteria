package ar.com.tecnoaccion.reporteria.exception;

import ar.com.tecnoaccion.reporteria.dto.ErrorMessage;
import ar.com.tecnoaccion.reporteria.utils.DefinedErrors;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class MiddleResponseErrorHandler extends DefaultResponseErrorHandler {

    private final Log logger = LogFactory.getLog(this.getClass());

    private static final String DEFAULT_CHARSET = "UTF-8";

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ErrorMessage result = new ErrorMessage(DefinedErrors.ERROR_DESCONOCIDO.getErrorCode(), DefinedErrors.ERROR_DESCONOCIDO.getErrorMessage());

        try {
            result = new ObjectMapper().readValue(getResponseBodyAsString(super.getResponseBody(response), super.getCharset(response)), ErrorMessage.class);
        } catch (JsonParseException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        throw new CuponException(result.getErrorCode(), result.getErrorMessage());
    }

    private String getResponseBodyAsString(byte[] responseBody, Charset responseCharset) {
        String charSet = (responseCharset != null ? responseCharset.name() : DEFAULT_CHARSET);

        try {
            return new String(responseBody, charSet);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }
}