package ru.icecode.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QQQ on 14.07.2014.
 */
public final class CommandFactory
{
    private static Map<Operation, Command> commandMap = new HashMap<>();

    static
    {
        commandMap.put(Operation.COMPARE, new CommandCompare());
        commandMap.put(Operation.EXPORT, new CommandExport());
    }

    public static void execute(String[] args)
    {
        if (args.length == 0)
            return;

        Operation operation = Operation.valueOf(args[0].toUpperCase());
        commandMap.get(operation).execute(args);
    }


    private CommandFactory() {}
}
