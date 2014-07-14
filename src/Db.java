import objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by w on 14.07.2014.
 */
public class Db implements AutoCloseable
{
    private final ConnectionString connectionString;
    private final Connection connection;

    public Db(ConnectionString connectionString) throws SQLException
    {
        this.connectionString = connectionString;
        this.connection = DriverManager.getConnection(connectionString.getValue());
    }

    public Connection getConnection() {
        return connection;
    }

    public List<User> getUsers() throws SQLException
    {
        List<User> users = new ArrayList<>();

        try (Statement stat = connection.createStatement())
        {
            ResultSet rs = stat.executeQuery("select username from all_users");

            while (rs.next())
            {
                users.add(new User(rs.getString("username")));
            }
        }

        return users;
    }

    @Override
    public void close() throws Exception
    {
        connection.close();
    }
}
