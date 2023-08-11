package org.fundacionjala.virtualassistant.mongo.exception;

public class RecordingException extends Exception {

  public static final String MESSAGE_RECORDING_NULL = "The object Recording is null";
  public static final String MESSAGE_NOT_WAV = "The provided file is not a valid .wav file";

  public RecordingException(String message) {
    super(message);
  }

  public RecordingException(String message, Throwable cause) {
    super(message, cause);
  }
}
