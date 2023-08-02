package org.fundacionjala.virtualassistant.repository;

import java.sql.*;

public class TextSaverImpl implements TextSaver {

    private static final String DB_URL = "jdbc:postgresql://dbUrl";
    private static final String DB_USER = "db_user";
    private static final String DB_PASSWORD = "database_password";

    @Override
    public boolean saveText(String text, int idRequest) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;

        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String updateQuery = "UPDATE db_schema.request SET text = ? WHERE id_request = ? RETURNING *;";
        stmt = connection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, text);
        stmt.setInt(2, idRequest);

        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
    }

}
