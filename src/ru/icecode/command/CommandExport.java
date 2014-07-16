package ru.icecode.command;

import ru.icecode.Db;
import ru.icecode.DbFactory;
import ru.icecode.objects.Trigger;
import ru.icecode.objects.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by QQQ on 15.07.2014.
 */
public class CommandExport implements Command
{
    @Override
    public void execute(String[] args)
    {
        String dbId = args[1];
        String[] userNames = args[2].split(";");
        String path = args[3];

        Db db = DbFactory.getDb(dbId);

        String triggersPath = path + "triggers\\";
        String functionsPath = path + "functions\\";
        String proceduresPath = path + "procedures\\";
        String packagesPath = path + "packages\\";
        String typesPath = path + "types\\";
        String viewsPath = path + "views\\";

        try
        {
            Map<String, User> users = db.getCustomUsers();

            for (String userName : userNames)
            {
                if (!users.containsKey(userName))
                {
                    System.out.println("Не найден пользователь " + userName);
                    continue;
                }

                User user = users.get(userName);

                for (Trigger trigger : user.getTriggers())
                {
                    saveToFile(triggersPath + trigger + ".TRG", trigger.getDdlScript());
                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void saveToFile(String fileName, String string) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(string);
        writer.close();
    }
}
