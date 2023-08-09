package org.fundacionjala.virtualassistant.repository;

import java.nio.file.Path;
import org.springframework.stereotype.Repository;

@Repository
public class WhisperClient implements ASRClient {

    public WhisperClient() {

    }

    @Override
    public String convertToText(Path audioFile)  {
        return "";
    }

}
