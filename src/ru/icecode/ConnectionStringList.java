package ru.icecode;

import ru.icecode.exception.IllegalConnectionString;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QQQ on 14.07.2014.
 */
public class ConnectionStringList
{
    private final String fileName;
    private final Map<String, ConnectionString> map = new HashMap<>();

    public ConnectionStringList(String fileName) throws FileNotFoundException, IOException
    {
        this.fileName = fileName;

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        while (reader.ready())
        {
            String line = reader.readLine().trim();

            if (line.length() == 0)
                continue;

            if (line.charAt(0) == ';')
                continue;

            String elementName = null;

            if (line.matches(".+=.+"))
            {
                elementName = line.substring(0, line.indexOf('=')).trim();
                line = line.substring(line.indexOf('=') + 1, line.length());
            }

            try
            {
                ConnectionString cs = new ConnectionString(line.trim());

                if (elementName == null)
                    elementName = cs.getSid();

                map.put(elementName, cs);
            }
            catch (IllegalConnectionString e)
            {
                ConsoleHelper.error(e.getMessage());
            }
        }
    }

    public Map<String, ConnectionString> getMap()
    {
        return map;
    }

    public String getFileName() {
        return fileName;
    }
}
