package ru.icecode.exception.RunTime;

/**
 * Created by QQQ on 15.07.2014.
 */
public class DbConnectionError extends RuntimeException
{
    public DbConnectionError(String message) {
        super(message);
    }
}
