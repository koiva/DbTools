package ru.icecode.objects;

import ru.icecode.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by w on 14.07.2014.
 */
public class User
{
    public static List<String> standardUsersNames = new ArrayList<>();
    {
        // Admins
        standardUsersNames.add("ANONYMOUS");
        standardUsersNames.add("CTXSYS");
        standardUsersNames.add("DBSNMP");
        standardUsersNames.add("EXFSYS");
        standardUsersNames.add("LBACSYS");
        standardUsersNames.add("MDSYS");
        standardUsersNames.add("MGMT_VIEW");
        standardUsersNames.add("OLAPSYS");
        standardUsersNames.add("ORDDATA");
        standardUsersNames.add("OWBSYS");
        standardUsersNames.add("ORDPLUGINS");
        standardUsersNames.add("ORDSYS");
        standardUsersNames.add("OUTLN");
        standardUsersNames.add("SI_INFORMTN_SCHEMA");
        standardUsersNames.add("SYS");
        standardUsersNames.add("SYSMAN");
        standardUsersNames.add("SYSTEM");
        standardUsersNames.add("TSMSYS");
        standardUsersNames.add("WK_TEST");
        standardUsersNames.add("WKSYS");
        standardUsersNames.add("WKPROXY");
        standardUsersNames.add("WMSYS");
        standardUsersNames.add("XDB");

        // Non-admins
        standardUsersNames.add("APEX_PUBLIC_USER");
        standardUsersNames.add("DIP");
        standardUsersNames.add("FLOWS_040100");
        standardUsersNames.add("FLOWS_FILES");
        standardUsersNames.add("MDDATA");
        standardUsersNames.add("ORACLE_OCM");
        standardUsersNames.add("SPATIAL_CSW_ADMIN_USR");
        standardUsersNames.add("SPATIAL_WFS_ADMIN_USR");
        standardUsersNames.add("XS$NULL");

        // hz
        standardUsersNames.add("APPQOSSYS");
        standardUsersNames.add("PERFSTAT");
        standardUsersNames.add("STATSPACK");
        standardUsersNames.add("AUDSYS");
        standardUsersNames.add("APEX_040200");
        standardUsersNames.add("PERFSTAT");


        standardUsersNames.add("GSMCATUSER");
        standardUsersNames.add("GSMUSER");
        standardUsersNames.add("GSMADMIN_INTERNAL");

        standardUsersNames.add("SYSKM");
        standardUsersNames.add("SYSDG");
        standardUsersNames.add("SYSBACKUP");

    }

    private final String name;
    private final Db owner;

    public User(Db owner, String name)
    {
        this.owner = owner;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String toString()
    {
        return name;
    }

    public List<Trigger> getTriggers() throws SQLException
    {
        List<Trigger> triggers = new ArrayList<>();

        try (PreparedStatement stat = owner.getConnection().prepareStatement("select * from all_triggers where owner = ?"))
        {
            stat.setString(1, name);
            ResultSet rs = stat.executeQuery();

            while (rs.next())
            {
                String triggerName = rs.getString("trigger_name");
                triggers.add(new Trigger(this, triggerName));
            }
        }

        return triggers;
    }

}
