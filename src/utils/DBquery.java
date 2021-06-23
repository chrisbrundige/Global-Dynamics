package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/** class establishes DBQuery*/
public class DBquery {

    private static PreparedStatement statement;

    /** method creates a prepared statement*/
    public static void setPreparedStatement(Connection conn,String sqlStatement) throws SQLException
    {
        statement = conn.prepareStatement(sqlStatement);
    }
    /** method gets a prepared statement*/
    public static PreparedStatement getPreparedStatement()
    {

        return statement;
    }
}
