package ru.icecode;

import oracle.jdbc.driver.OracleDriver;
import ru.icecode.exception.IllegalConnectionString;
import ru.icecode.objects.Trigger;
import ru.icecode.objects.User;

import java.io.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

            try (Db dataBase21 = new Db(connectionStrings.get(1));
                 Db dataBase22 = new Db(connectionStrings.get(2));
            )
            {
                Map<String, User> users1 = dataBase21.getCustomUsers();
                Map<String, User> users2 = dataBase22.getCustomUsers();

                User u1 = users1.get("LPU");
                User u2 = users2.get("LPU");

                List<Trigger> triggers1 = u1.getTriggers();
                List<Trigger> triggers2 = u2.getTriggers();

                List<String> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                List<String> delete = new ArrayList<>();
                List<String> add = new ArrayList<>();

                for (Trigger trigger : triggers1)
                    list1.add(trigger.toString());

                /*
                for (Trigger trigger : triggers2)
                    list2.add(trigger.toString());
*/

                File myFolder = new File("D:\\OraObj\\Triggers");
                File[] files = myFolder.listFiles();
                for (File file : files)
                {
                    if (file.getName().matches("^LPU\\..+"))
                        list2.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
                }

                ListComparator.compare(list1, list2, delete, add);

                System.out.println("Сравнение баз");

                System.out.println("Удаленно");
                System.out.println(delete.toString());

                System.out.println("Добавленно");
                System.out.println(add.toString());
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


