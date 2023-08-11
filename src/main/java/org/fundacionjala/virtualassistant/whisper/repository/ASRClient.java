package org.fundacionjala.virtualassistant.whisper.repository;
public interface ASRClient {
    
    String convertToText(String audioFile);
    
}
