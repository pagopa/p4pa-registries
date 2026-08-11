package it.gov.pagopa.pu.registry.exception.transcoder.handler;

import it.gov.pagopa.pu.registry.dto.generated.ErrorDTO;
import it.gov.pagopa.pu.registry.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.pu.registry.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.web.client.HttpClientErrorException;

public class HttpClientTooManyRequestExceptionMessageTranscoder implements ExceptionMessageTranscoder<HttpClientErrorException.TooManyRequests> {
  @Override
  public ExceptionMessageTranscoded transcode(HttpClientErrorException.TooManyRequests tooManyRequestsException) {
    return new ExceptionMessageTranscoded(
      ErrorDTO.CategoryEnum.TOO_MANY_REQUESTS.getValue(),
      tooManyRequestsException.getMessage(),
      null);
  }
}
