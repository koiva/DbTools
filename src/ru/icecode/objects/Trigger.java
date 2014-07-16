package ru.icecode.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by w on 14.07.2014.
 */
public class Trigger extends SchemaObject
{
    public Trigger(User owner, ResultSet rs) throws SQLException
    {
        super(owner, rs.getString("trigger_name"));

        StringBuffer sb = new StringBuffer("CREATE OR REPLACE TRIGGER ");

        sb.append(rs.getString("description"));
        sb.append(rs.getString("trigger_body"));

        ddlScript = sb.toString();
    }
}
