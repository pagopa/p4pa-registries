package it.gov.pagopa.pu.registry.exception.transcoder.handler;

import it.gov.pagopa.pu.registry.dto.generated.ErrorDTO;
import it.gov.pagopa.pu.registry.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.pu.registry.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;

public class DataIntegrityViolationExceptionMessageTranscoder implements ExceptionMessageTranscoder<DataIntegrityViolationException> {

  @Override
  public ExceptionMessageTranscoded transcode(DataIntegrityViolationException dataIntegrityViolationException) {
    String errorMsg = "Conflict.";
    if(dataIntegrityViolationException.getCause() instanceof MongoDataIntegrityViolationException mongoDataIntegrityViolationException) {
      errorMsg += " " + mongoDataIntegrityViolationException.getMostSpecificCause().getMessage();
    }
    return new ExceptionMessageTranscoded(
      ErrorDTO.CategoryEnum.CONFLICT.getValue(),
      errorMsg,
      null) ;
  }
}
