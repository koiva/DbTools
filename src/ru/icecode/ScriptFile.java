package ru.icecode;

import ru.icecode.exception.RunTime.FileReadError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQQ on 15.07.2014.
 */
public class ScriptFile
{
    private final String fileName;

    private List<String> lines = new ArrayList<>();

    public ScriptFile(String fileName)
    {
        this.fileName = fileName;

        try
        {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while (reader.ready()) {
                String line = reader.readLine().trim();

                if (line.length() == 0)
                    continue;

                if (line.charAt(0) == ';')
                    continue;

                lines.add(line);
            }
        } catch (Exception e)
        {
            throw new FileReadError("Ошибка при чтении файла: " + e.toString());
        }
    }

    public List<String> getLines() {
        return lines;
    }
}
