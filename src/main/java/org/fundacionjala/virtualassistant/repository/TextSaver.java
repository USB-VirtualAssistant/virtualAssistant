package org.fundacionjala.virtualassistant.repository;

import java.sql.SQLException;

public interface TextSaver {
    boolean saveText(String text,int idRequest) throws SQLException;
}
