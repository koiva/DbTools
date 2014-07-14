package ru.icecode.exception.RunTime;

/**
 * Created by QQQ on 14.07.2014.
 */
public class NotFindTnsnames extends RuntimeException
{
    public NotFindTnsnames() {
        super("Пожалуйста установите значение системной перменной TNS_ADMIN, которая указыват на каталог с файлом tnsnames.ora.");
    }
}
