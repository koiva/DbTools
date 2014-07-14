package ru.icecode;

import ru.icecode.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println(connectionString.getValue());
        this.connection = DriverManager.getConnection(connectionString.getValue());
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<String, User> getUsers() throws SQLException
    {
        Map<String, User> users = new HashMap<>();

        try (Statement stat = connection.createStatement())
        {
            ResultSet rs = stat.executeQuery("select username from all_users");

            while (rs.next())
            {
                String userName = rs.getString("username");
                users.put(userName, new User(this, userName));
            }
        }

        return users;
    }

    public Map<String, User> getCustomUsers() throws SQLException
    {
        Map<String, User> users = new HashMap<>();

        try (Statement stat = connection.createStatement())
        {
            ResultSet rs = stat.executeQuery("select username from all_users");

            while (rs.next())
            {
                String userName = rs.getString("username");
                if (!User.standardUsersNames.contains(userName))
                    users.put(userName, new User(this, userName));
            }
        }

        return users;
    }

    public Map<String, User> getDefaultUsers() throws SQLException
    {
        Map<String, User> users = new HashMap<>();

        try (Statement stat = connection.createStatement())
        {
            ResultSet rs = stat.executeQuery("select username from all_users");

            while (rs.next())
            {
                String userName = rs.getString("username");
                if (User.standardUsersNames.contains(userName))
                    users.put(userName, new User(this, userName));
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
