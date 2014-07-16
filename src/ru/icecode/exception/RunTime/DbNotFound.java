package ru.icecode.exception.RunTime;

/**
 * Created by QQQ on 15.07.2014.
 */
public class DbNotFound extends RuntimeException
{
    public DbNotFound(String message) {
        super(message);
    }
}
