package ru.icecode;

import java.util.List;

/**
 * Created by w on 14.07.2014.
 */
public class ListComparator
{
    public static boolean compare(List<String> list1, List<String> list2, List<String> delete, List<String> add)
    {
        delete.clear();
        add.clear();

        boolean res = true;

        for (String str : list1)
            if (!list2.contains(str))
            {
                delete.add(str);
                res = false;
            }

        for (String str : list2)
            if (!list1.contains(str))
            {
                add.add(str);
                res = false;
            }

        return res;
    }


}
