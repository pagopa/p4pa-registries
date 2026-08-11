package it.gov.pagopa.pu.registry.exception.transcoder;

public interface ExceptionMessageTranscoder<T extends Exception> {
  ExceptionMessageTranscoded transcode(T exception);
}
