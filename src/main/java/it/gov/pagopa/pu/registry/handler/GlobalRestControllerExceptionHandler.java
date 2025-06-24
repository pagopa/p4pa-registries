package it.gov.pagopa.pu.registry.handler;

import it.gov.pagopa.pu.registry.dto.generated.ErrorDTO;
import it.gov.pagopa.pu.registry.exception.ApplicationRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {

  @ExceptionHandler(ApplicationRestException.class)
  public ResponseEntity<ErrorDTO> handleApplicationRestException(ApplicationRestException e) {
    ErrorDTO.CodeEnum code = switch (e.getHttpStatus()) {
      case BAD_REQUEST -> ErrorDTO.CodeEnum.BAD_REQUEST;
      case NOT_FOUND -> ErrorDTO.CodeEnum.NOT_FOUND;
      default -> ErrorDTO.CodeEnum.GENERIC_ERROR;
    };

    var errorDTO = ErrorDTO.builder()
      .code(code)
      .message(e.getMessage())
      .build();

    return ResponseEntity.status(e.getHttpStatus()).body(errorDTO);
  }
}
