package ru.icecode;

import oracle.jdbc.driver.OracleDriver;
import ru.icecode.exception.RunTime.NotFindTnsnames;
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

    public DbTools() {
        defineTnsAdmin();
    }

    private void defineTnsAdmin()
    {
        String path = System.getenv("TNS_ADMIN");

        if (path == null)
            throw new NotFindTnsnames();

        ConsoleHelper.println("TNS_ADMIN = " + path);

        System.setProperty("oracle.net.tns_admin", path);
    }

    public static void main(String[] args) throws Exception
    {
        ConsoleHelper.println("Иструменты для базы данных Oracle.");

        DriverManager.registerDriver(new OracleDriver());
        DbTools dbTools = new DbTools();

        try
        {
            ConnectionStringList connectionStringList = new ConnectionStringList(CONNECTION_STRINGS_FILENAME);

            ConsoleHelper.println("Загруженные БД:");
            for (ConnectionString str : connectionStringList.getMap().values())
            {
                System.out.println(str);
            }

            try (Db dataBase21 = new Db(connectionStringList.getMap().get("NVDS"))
            )
            {
                Map<String, User> users1 = dataBase21.getCustomUsers();


                User u1 = users1.get("LPU");


                List<Trigger> triggers1 = u1.getTriggers();


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

                File myFolder = new File("D:\\Work\\OraObj\\Triggers");
                File[] files = myFolder.listFiles();
                for (File file : files)
                {
                    if (file.getName().matches("^LPU\\..+"))
                        list2.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
                }

                ListComparator.compare(list1, list2, delete, add);

                ConsoleHelper.println("Сравнение баз");

                ConsoleHelper.println("Удаленно");
                ConsoleHelper.println(delete.toString());

                ConsoleHelper.println("Добавленно");
                ConsoleHelper.println(add.toString());
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


