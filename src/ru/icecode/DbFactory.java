package ru.icecode;

import oracle.jdbc.driver.OracleDriver;
import ru.icecode.exception.RunTime.DbConnectionError;
import ru.icecode.exception.RunTime.DbNotFound;
import ru.icecode.exception.RunTime.NotFindTnsnames;
import ru.icecode.objects.Trigger;
import ru.icecode.objects.User;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DbFactory implements AutoCloseable
{
    private static ConnectionStringMap connectionStringMap;
    private static DbFactory dbFactory;
    private static Map<String, Db> dbMap = new HashMap<>();


    public static Db getDb(String id)
    {
        if (dbMap.containsKey(id))
            return dbMap.get(id);

        if (!connectionStringMap.getMap().containsKey(id))
            throw new DbNotFound("Не найдена база данных с идентификатором " + id);

        try
        {
            Db db = new Db(connectionStringMap.getMap().get(id));
            dbMap.put(id, db);
            return db;
        }
        catch (SQLException e)
        {
            throw new DbConnectionError("Не удалось подключится к базе данных " + id + " " + e.toString());
        }
    }

    public DbFactory(ConnectionStringMap connectionStringMap)
    {
        if (dbFactory != null)
            throw new RuntimeException("DbFactory !!!");

        dbFactory = this;
        DbFactory.connectionStringMap = connectionStringMap;
    }

    @Override
    public void close() throws Exception
    {
        for (Db db : dbMap.values())
        {
            db.close();
        }
    }
}




