package org.fundacionjala.virtualassistant.mongo.exception;

public class RecordingException extends Exception {

  public static final String MESSAGE_RECORDING_NULL = "The object Recording is null";

  public RecordingException(String message) {
    super(message);
  }

  public RecordingException(String message, Throwable cause) {
    super(message, cause);
  }
}
