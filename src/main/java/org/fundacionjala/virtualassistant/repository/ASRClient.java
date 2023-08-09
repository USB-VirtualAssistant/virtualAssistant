package org.fundacionjala.virtualassistant.repository;

import org.springframework.stereotype.Repository;
import java.nio.file.Path;

@Repository
public interface ASRClient {

    String convertToText(Path audioFile);
}