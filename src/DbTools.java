import exception.IllegalConnectionString;
import objects.User;
import oracle.jdbc.driver.OracleDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DbTools
{

    private static final String PROGRAM_DIR = DbTools.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final String PROGRAM_PATH = PROGRAM_DIR + '\\';

    private static final String CONNECTION_STRINGS_FILENAME = PROGRAM_PATH + "db_list.txt";

    private boolean defineTnsAdmin()
    {
        String path = System.getenv("TNS_ADMIN");

        if (path == null)
            return false;

        System.setProperty("oracle.net.tns_admin", path);
        return true;
    }

    private List<ConnectionString> loadConnectionStrings() throws FileNotFoundException, IOException
    {
        List<ConnectionString> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(CONNECTION_STRINGS_FILENAME));

        while (reader.ready())
        {
            String line = reader.readLine().trim();

            if (line.length() == 0)
                continue;

            try
            {
                list.add(new ConnectionString(line));
            }
            catch (IllegalConnectionString e)
            {
                System.err.println(e.getMessage());
            }
        }

        return list;
    }


    public static void main(String[] args) throws Exception
    {
        DriverManager.registerDriver(new OracleDriver());

        DbTools dbTools = new DbTools();


        System.out.println("Иструменты для базы данных Oracle.");

        if (!dbTools.defineTnsAdmin())
        {
            System.out.println("Пожалуйста установите значение системной перменной TNS_ADMIN, которая указыват на каталог с файлом tnsnames.ora.");
            return;
        }

        try
        {
            List<ConnectionString> connectionStrings = dbTools.loadConnectionStrings();

            System.out.println("Загруженные БД:");
            for (ConnectionString str : connectionStrings)
            {
                System.out.println(str);
            }

            try (Db db = new Db(connectionStrings.get(0)))
            {
                for (User user : db.getUsers())
                {
                    System.out.println(user);
                }

            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }


    }
}

/* TODO


                Statement stmt = db.getConnection().createStatement();
                try {
                    ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
                    try {
                        while (rset.next())
                            System.out.println (rset.getString(1));   // Print col 1
                    } finally {
                        try { rset.close(); } catch (Exception ignore) {}
                    }
                } finally {
                    try { stmt.close(); } catch (Exception ignore) {}
                }


 */


