package ru.icecode.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by QQQ on 14.07.2014.
 */
public class View extends SchemaObject
{
    public View(User owner, ResultSet rs) throws SQLException
    {
        super(owner, rs.getString("type_name"));

        ddlScript = rs.getString("ddl").trim();
    }
}
