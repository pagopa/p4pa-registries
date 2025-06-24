package it.gov.pagopa.pu.registry.exception;

import lombok.Getter;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;

@StandardException
@Getter
public class ApplicationRestException extends ApplicationException {
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public ApplicationRestException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
