package ru.icecode;

import ru.icecode.exception.IllegalConnectionString;

/**
 * Created by w on 14.07.2014.
 */
public class ConnectionString
{
    private static final String PREFIX = "jdbc:oracle:thin:";

    private String value;
    private String sid;
    private String user;
    private String password;


    public ConnectionString(String connectionString) throws IllegalConnectionString
    {
        if (!connectionString.matches("\\w+/\\w+@\\w+"))
            throw new IllegalConnectionString("Неверный формат строки подключения " + connectionString);

        value = PREFIX + connectionString;
        user = connectionString.substring(0, connectionString.indexOf("/"));
        password = connectionString.substring(connectionString.indexOf("/") + 1, connectionString.indexOf("@"));
        sid = connectionString.substring(connectionString.indexOf("@"), connectionString.length());
    }

    public String getValue()
    {
        return value;
    }

    public String getSid() {
        return sid;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Sid: %s User: %s", getSid(), getUser());
    }
}
