package ru.icecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by QQQ on 14.07.2014.
 */
public class ConsoleHelper
{
    public static void println(String str)
    {
        System.out.println(str);
    }
    public static void error(String str)
    {
        System.err.println(str);
    }

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String read()
    {
        try
        {
            return reader.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    private ConsoleHelper() {
        super();
    }
}
