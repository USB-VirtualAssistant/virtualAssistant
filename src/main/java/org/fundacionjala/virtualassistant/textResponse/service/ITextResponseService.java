package org.fundacionjala.virtualassistant.textResponse.service;

import org.fundacionjala.virtualassistant.textResponse.response.TextResponse;

public interface ITextResponseService {
    TextResponse save(long idRequest, String text);
}
