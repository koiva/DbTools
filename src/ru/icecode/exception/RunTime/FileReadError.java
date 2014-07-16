package ru.icecode.exception.RunTime;

/**
 * Created by QQQ on 15.07.2014.
 */
public class FileReadError extends RuntimeException
{
    public FileReadError(String message) {
        super(message);
    }
}
